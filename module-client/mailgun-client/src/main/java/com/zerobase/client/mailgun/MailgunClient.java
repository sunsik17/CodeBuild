package com.zerobase.client.mailgun;


import com.zerobase.client.mailgun.dto.MailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
	@PostMapping("sandboxef774994c5654fbcb9159d4cd3dbcdaf.mailgun.org/messages")
	ResponseEntity<String> sendEmail(@SpringQueryMap MailForm form);
}
