
export interface MessageItem {
  role: 'user' | 'assistant';
  content: string;
}

interface MessageProps {
  message: MessageItem;
}

export default function Message({message}: MessageProps) {
  return (
    <div className="mb-l">
      <div className="font-bold">{message.role === 'user' ? '🧑‍💻 You' : '🤖 Assistant'}</div>
      <div>
          {message.content}
      </div>
    </div>
  )
};
