package com.challet.challetservice.domain.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/challet/upload")
@RequiredArgsConstructor
public class FileUploadController {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@PostMapping
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String uniqueFileName = UUID.randomUUID().toString() + ".png";
			String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + uniqueFileName;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			Thumbnails.of(file.getInputStream())
				.width(500)
				.outputQuality(0.5)
				.outputFormat("jpeg")
				.toOutputStream(outputStream);


			byte[] imageData = outputStream.toByteArray();

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("image/png");
			metadata.setContentLength(imageData.length);

			ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
			amazonS3Client.putObject(bucket, uniqueFileName, inputStream, metadata);

			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
		}
	}
}
