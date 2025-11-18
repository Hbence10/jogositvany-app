package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
}
