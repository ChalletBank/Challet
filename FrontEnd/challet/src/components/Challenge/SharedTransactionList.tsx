import { useEffect, useState, useRef } from 'react';
import TransactionListItem from './TransactionItem';
import useAuthStore from '../../store/useAuthStore';
import webSocketService from '../../hooks/websocket'; // 웹소켓 서비스 추가
import { useChallengeApi } from '../../hooks/useChallengeApi'; // API 함수 추가
import { Transaction } from './TransactionType'; // Transaction 타입 추가
import { format } from 'date-fns'; // 날짜 형식화 라이브러리
import { ko } from 'date-fns/locale'; // 한국어 로케일

const TransactionList = ({ challengeId }: { challengeId: number }) => {
  const [sharedTransactions, setSharedTransactions] = useState<Transaction[]>(
    []
  );
  const [isNewMessageButtonVisible, setIsNewMessageButtonVisible] =
    useState(false); // 새 메시지 버튼 표시 여부 상태
  const cursorRef = useRef<number | null>(null);
  const hasNextPageRef = useRef(true);
  const isLoadingRef = useRef(false); // isLoading을 useRef로 변경
  const transactionListRef = useRef<HTMLDivElement>(null);
  const isFetchingRef = useRef(false);
  const userId = Number(useAuthStore.getState().userId);
  const { fetchSharedTransactions } = useChallengeApi();

  useEffect(() => {
    const connectAndSubscribe = async () => {
      try {
        if (!webSocketService.isConnected()) {
          await webSocketService.connect();
        }

        webSocketService.subscribeTransaction(
          challengeId.toString(),
          (message) => {
            const receivedTransaction = JSON.parse(message.body);

            if (receivedTransaction.action === 'ADD') {
              const transaction: Transaction = {
                sharedTransactionId: receivedTransaction.id,
                transactionDateTime: new Date().toISOString(),
                goodCount: 0,
                sosoCount: 0,
                badCount: 0,
                commentCount: 0,
                userEmoji: null,
                image: receivedTransaction.image,
                deposit: receivedTransaction.deposit,
                transactionAmount: receivedTransaction.transactionAmount,
                content: receivedTransaction.content,
                userId: receivedTransaction.userId,
                nickname: receivedTransaction.nickname,
                profileImage: receivedTransaction.profileImage,
              };

              setSharedTransactions((prevTransactions) => {
                const isDuplicate = prevTransactions.some(
                  (t) =>
                    t.sharedTransactionId === transaction.sharedTransactionId
                );
                if (!isDuplicate) {
                  const updatedTransactions = [
                    ...prevTransactions,
                    transaction,
                  ];
                  return updatedTransactions.sort(
                    (a, b) =>
                      new Date(a.transactionDateTime).getTime() -
                      new Date(b.transactionDateTime).getTime()
                  );
                }
                return prevTransactions;
              });

              // 사용자가 현재 스크롤을 맨 아래로 내리지 않았을 때 버튼을 보여줌
              if (
                transactionListRef.current &&
                transactionListRef.current.scrollTop +
                  transactionListRef.current.clientHeight <
                  transactionListRef.current.scrollHeight - 100
              ) {
                console.log('새 메시지 버튼 표시');
                setIsNewMessageButtonVisible(true);
              }
            } else if (receivedTransaction.action === 'UPDATE') {
              setSharedTransactions((prevTransactions) =>
                prevTransactions.map((t) =>
                  t.sharedTransactionId === receivedTransaction.id
                    ? {
                        ...t,
                        image: receivedTransaction.image,
                        deposit: receivedTransaction.deposit,
                        transactionAmount:
                          receivedTransaction.transactionAmount,
                        content: receivedTransaction.content,
                      }
                    : t
                )
              );
            }
          }
        );

        webSocketService.subscribeEmoji(challengeId.toString(), (message) => {
          const emojiUpdate = JSON.parse(message.body);

          setSharedTransactions((prevTransactions) =>
            prevTransactions.map((transaction) =>
              transaction.sharedTransactionId ===
              emojiUpdate.sharedTransactionId
                ? {
                    ...transaction,
                    goodCount: emojiUpdate.emoji.goodCount,
                    sosoCount: emojiUpdate.emoji.sosoCount,
                    badCount: emojiUpdate.emoji.badCount,
                    userEmoji:
                      emojiUpdate.userId === userId
                        ? emojiUpdate.emoji.userEmoji
                        : transaction.userEmoji,
                  }
                : transaction
            )
          );
        });
      } catch (error) {
        console.error('WebSocket 연결 실패:', error);
      }
    };
    connectAndSubscribe();
  }, [challengeId]);

  const fetchTransactions = async (scrollToBottom = false) => {
    if (
      isFetchingRef.current ||
      !hasNextPageRef.current ||
      isLoadingRef.current
    )
      return;

    isFetchingRef.current = true;
    isLoadingRef.current = true;

    const previousScrollHeight = transactionListRef.current?.scrollHeight || 0;
    const previousScrollTop = transactionListRef.current?.scrollTop || 0;

    const response = await fetchSharedTransactions(
      challengeId,
      cursorRef.current
    );

    if (response && response.history) {
      const reversedHistory = [...response.history].reverse();

      setSharedTransactions((prev) => {
        const newTransactions = reversedHistory.filter(
          (transaction) =>
            !prev.some(
              (t) => t.sharedTransactionId === transaction.sharedTransactionId
            )
        );
        return [...newTransactions, ...prev];
      });

      const lastTransaction = response.history[response.history.length - 1];
      if (lastTransaction) {
        cursorRef.current = lastTransaction.sharedTransactionId;
      }

      hasNextPageRef.current = response.hasNextPage;

      if (scrollToBottom && transactionListRef.current) {
        setTimeout(() => {
          const newScrollHeight = transactionListRef.current!.scrollHeight;
          transactionListRef.current!.scrollTop =
            newScrollHeight - previousScrollHeight + previousScrollTop;
        }, 50);
      } else if (!scrollToBottom && transactionListRef.current) {
        setTimeout(() => {
          transactionListRef.current!.scrollTop =
            transactionListRef.current!.scrollHeight - previousScrollHeight;
        }, 50);
      }
    }
    isFetchingRef.current = false;
    isLoadingRef.current = false;
  };

  const handleScroll = () => {
    if (
      transactionListRef.current &&
      transactionListRef.current.scrollTop < 400 &&
      hasNextPageRef.current &&
      !isLoadingRef.current &&
      !isFetchingRef.current
    ) {
      fetchTransactions();
    }

    // 사용자가 수동으로 맨 아래까지 스크롤하면 버튼 숨기기
    if (
      transactionListRef.current &&
      transactionListRef.current.scrollTop +
        transactionListRef.current.clientHeight >=
        transactionListRef.current.scrollHeight - 100
    ) {
      setIsNewMessageButtonVisible(false);
    }
  };

  useEffect(() => {
    fetchTransactions(true);
    const ref = transactionListRef.current;
    if (ref) {
      ref.addEventListener('scroll', handleScroll);
    }
    return () => {
      if (ref) {
        ref.removeEventListener('scroll', handleScroll);
      }
    };
  }, []);

  const handleEmojiClick = (transaction: Transaction, emojiType: string) => {
    let action = 'ADD';
    let beforeType = transaction.userEmoji;

    if (beforeType === emojiType) {
      action = 'DELETE';
    } else if (beforeType) {
      action = 'UPDATE';
    }

    const emojiRequest = {
      sharedTransactionId: transaction.sharedTransactionId,
      action: action,
      type: emojiType,
    };

    webSocketService.sendMessage(
      `/app/challenges/${challengeId}/emoji`,
      emojiRequest
    );
  };

  const handleScrollToBottom = () => {
    if (transactionListRef.current) {
      transactionListRef.current.scrollTop =
        transactionListRef.current.scrollHeight;
    }
    setIsNewMessageButtonVisible(false); // 스크롤을 내리면 버튼을 숨김
  };

  return (
    <div className='relative'>
      {/* 새로운 메시지 도착 시 하단에 버튼 표시 */}
      {isNewMessageButtonVisible && (
        <button
          className='fixed bottom-24 bg-[#00CCCC] text-white px-4 py-2 rounded-md shadow-lg'
          onClick={handleScrollToBottom}
        >
          새로운 메시지 보기
        </button>
      )}

      {/* 거래내역 목록 */}
      <div
        className='scrollbar-hide overflow-y-auto max-h-[70vh]'
        ref={transactionListRef}
      >
        {sharedTransactions.map((transaction, index) => {
          const previousTransaction = sharedTransactions[index - 1];
          const currentDate = new Date(
            transaction.transactionDateTime
          ).toDateString();
          const previousDate = previousTransaction
            ? new Date(previousTransaction.transactionDateTime).toDateString()
            : null;

          const isDifferentDate = currentDate !== previousDate;

          return (
            <div key={transaction.sharedTransactionId}>
              {isDifferentDate && (
                <div className='text-center text-gray-500 my-2'>
                  {format(
                    new Date(transaction.transactionDateTime),
                    'yyyy년 MM월 dd일',
                    { locale: ko }
                  )}
                </div>
              )}
              <TransactionListItem
                challengeId={challengeId}
                transaction={transaction}
                userId={userId}
                handleEmojiClick={handleEmojiClick}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default TransactionList;
