import React from 'react';
import { Comment } from 'semantic-ui-react';

export interface MessageItem {
  role: 'user' | 'assistant';
  content: string;
}

interface MessageProps {
  message: MessageItem;
}

export default function Message({ message }: MessageProps) {
  const isBot = message.role === 'assistant';
  return (
    <Comment>
      <Comment.Content>
        <Comment.Author as="a">
          {isBot ? '🤖 Assistant' : '🧑‍💻 You'}
        </Comment.Author>
        <Comment.Text>{message.content}</Comment.Text>
      </Comment.Content>
    </Comment>
  );
}