package com.kjh.ollama.langserve.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class OllamaChatService {
    private final OllamaChatModel chatModel;

    public Map generate(String message){
        return Map.of("generation", chatModel.call(message));
    }

    public Flux<ChatResponse> generatesStream(String message){
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}
