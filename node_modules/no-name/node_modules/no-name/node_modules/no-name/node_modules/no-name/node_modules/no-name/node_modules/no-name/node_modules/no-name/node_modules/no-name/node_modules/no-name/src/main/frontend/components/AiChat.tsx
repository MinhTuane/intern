import React, { useRef, useEffect } from 'react';
import { Comment } from 'semantic-ui-react';
import Message, { MessageItem } from './Message';
import TypingIndicator from './TypingIndicator';

interface AiChatProps {
  messages: MessageItem[];
  working?: boolean;
}

export default function AiChat({ messages,working }: AiChatProps) {
  const endOfMessagesRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (endOfMessagesRef.current) {
      endOfMessagesRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);

  return (
    <Comment.Group style={{ maxHeight: "60vh", overflowY: "auto", marginBottom: "1rem" }}>
      {messages.map((msg, index) => (
        <Message key={index} message={msg} />
      ))}
      {working && <TypingIndicator />}
      <div ref={endOfMessagesRef} />
    </Comment.Group>
  );
}