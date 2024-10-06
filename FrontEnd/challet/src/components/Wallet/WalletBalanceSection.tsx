import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../store/useAuthStore';
import useAccountStore from '../../store/useAccountStore';
import AxiosInstance from '../../api/axiosInstance';
import axios from 'axios';

interface Account {
  id: number;
  accountNumber: string;
  accountBalance: number;
}

interface AccountResponse {
  accountCount: number;
  accounts: Account[];
}

const WalletBalanceSection = () => {
  const navigate = useNavigate();
  const [accountInfo, setAccountInfo] = useState<Account | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const { setAccountInfo: setStoreAccountInfo } = useAccountStore();
  const { accessToken } = useAuthStore();

  const fetchAccountInfo = useCallback(async () => {
    if (!accessToken) {
      setLoading(false);
      setError(true);
      setErrorMessage('유효하지 않은 토큰입니다.');
      return;
    }

    try {
      const response = await AxiosInstance.get<AccountResponse>('/api/ch-bank');
      const account = response.data.accounts[0];

      if (account) {
        setAccountInfo(account);
        setStoreAccountInfo(account);
        console.log('계좌 정보가 성공적으로 불러와졌습니다:', account);
      } else {
        setError(true);
        setErrorMessage('계좌 정보가 없습니다.');
        console.log('계좌 정보가 존재하지 않습니다.');
      }
    } catch (error) {
      handleError(error);
    } finally {
      setLoading(false);
    }
  }, [accessToken, setStoreAccountInfo]);

  const handleError = (error: unknown) => {
    if (axios.isAxiosError(error)) {
      const message =
        error.response?.data?.errorMessage || '알 수 없는 오류가 발생했습니다.';
      setErrorMessage(
        error.response?.status === 400 &&
          error.response?.data?.errorCode === 'NotFoundUserAccountException'
          ? '계좌가 존재하지 않습니다.'
          : `오류: ${message}`
      );
      console.log('Axios 오류 발생:', message);
    } else {
      setErrorMessage('요청을 보내는 중 오류가 발생했습니다.');
      console.log('알 수 없는 오류가 발생했습니다.', error);
    }
    setError(true);
  };

  useEffect(() => {
    fetchAccountInfo();
  }, [fetchAccountInfo]);

  if (loading) {
    return (
      <div className='w-full bg-white p-4 rounded-lg shadow-md mb-8'>
        <p className='text-xs font-bold mt-2 mb-4'>
          계좌 정보를 불러오는 중입니다.
        </p>
      </div>
    );
  }

  if (error || !accountInfo) {
    // 에러가 발생했거나 계좌 정보가 없을 때
    return (
      <div className='w-full bg-white p-4 rounded-lg shadow-md mb-8'>
        <p className='text-xs font-bold mt-2 mb-4'>
          {error ? errorMessage : '계좌가 존재하지 않습니다.'}
        </p>
      </div>
    );
  }

  // 계좌 정보가 있을 때
  return (
    <div
      className=' bg-white p-4 rounded-lg shadow-md mb-8 cursor-pointer'
      onClick={() => navigate('/history')}
      style={{ width: '97%' }}
    >
      <div className='text-left'>
        <h2 className='text-sm font-medium text-[#6C6C6C] mb-4'>
          챌렛계좌 {` ${accountInfo.accountNumber}`}
        </h2>
        <p className='text-2xl font-bold mt-2 mb-4'>
          {accountInfo.accountBalance.toLocaleString()}원
        </p>
      </div>
      <div className='flex justify-end gap-2'>
        <button
          className='border border-gray-300 rounded-lg px-3 py-1 text-sm text-[#6C6C6C] bg-white'
          onClick={(e) => {
            e.stopPropagation();
            navigate('/transfer', {
              state: {
                accountBalance: accountInfo.accountBalance,
                accountId: accountInfo.id,
              },
            });
          }}
        >
          송금
        </button>
        <button
          className='border border-gray-300 rounded-lg px-3 py-1 text-sm text-[#6C6C6C] bg-white'
          onClick={(e) => {
            e.stopPropagation();
            navigate('/history');
          }}
        >
          내역
        </button>
      </div>
    </div>
  );
};

export default WalletBalanceSection;