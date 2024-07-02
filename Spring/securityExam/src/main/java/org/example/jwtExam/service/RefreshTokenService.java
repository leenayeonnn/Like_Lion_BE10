package org.example.jwtExam.service;

import java.util.Optional;

import org.example.jwtExam.domain.RefreshToken;
import org.example.jwtExam.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional(readOnly = false)
	public RefreshToken addRefreshToken(RefreshToken refreshToken) {
		return refreshTokenRepository.save(refreshToken);
	}

	@Transactional(readOnly = true)
	public Optional<RefreshToken> findRefreshToken(String refreshToken) {
		return refreshTokenRepository.findByValue(refreshToken);
	}

	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.findByValue(refreshToken).ifPresent(refreshTokenRepository::delete);
	}
}
