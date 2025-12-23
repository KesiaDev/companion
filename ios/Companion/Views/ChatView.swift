import SwiftUI

struct ChatView: View {
    @StateObject private var chatViewModel = ChatViewModel()
    @State private var messageText = ""
    
    var body: some View {
        NavigationView {
            VStack {
                ScrollViewReader { proxy in
                    ScrollView {
                        LazyVStack(alignment: .leading, spacing: 12) {
                            // Mensagem inicial
                            if chatViewModel.messages.isEmpty {
                                initialMessage
                            }
                            
                            ForEach(chatViewModel.messages) { message in
                                ChatBubble(message: message)
                            }
                        }
                        .padding()
                    }
                    .onChange(of: chatViewModel.messages.count) { _ in
                        if let lastMessage = chatViewModel.messages.last {
                            withAnimation {
                                proxy.scrollTo(lastMessage.id, anchor: .bottom)
                            }
                        }
                    }
                }
                
                if chatViewModel.requiresSupport {
                    supportBanner
                }
                
                messageInput
            }
            .navigationTitle("Companion")
            .navigationBarTitleDisplayMode(.inline)
        }
        .onAppear {
            if chatViewModel.messages.isEmpty {
                chatViewModel.addInitialMessage()
            }
        }
    }
    
    private var initialMessage: some View {
        ChatBubble(message: ChatMessage(
            text: "Oi. Eu sou o Companion. Estou aqui para te acompanhar ao longo do seu dia. Você pode conversar comigo sempre que quiser.",
            isUser: false
        ))
    }
    
    private var supportBanner: some View {
        HStack {
            Image(systemName: "exclamationmark.triangle.fill")
                .foregroundColor(.orange)
            VStack(alignment: .leading) {
                Text("Apoio Profissional")
                    .fontWeight(.bold)
                Text("Se você está passando por um momento difícil, considere buscar ajuda profissional. CVV: 188")
                    .font(.caption)
            }
            Spacer()
        }
        .padding()
        .background(Color.orange.opacity(0.1))
        .cornerRadius(8)
        .padding(.horizontal)
    }
    
    private var messageInput: some View {
        HStack {
            TextField("Digite sua mensagem...", text: $messageText, axis: .vertical)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .lineLimit(1...4)
            
            Button(action: {
                if !messageText.isEmpty {
                    chatViewModel.sendMessage(messageText)
                    messageText = ""
                }
            }) {
                if chatViewModel.isLoading {
                    ProgressView()
                } else {
                    Image(systemName: "paperplane.fill")
                }
            }
            .disabled(messageText.isEmpty || chatViewModel.isLoading)
        }
        .padding()
    }
}

struct ChatBubble: View {
    let message: ChatMessage
    
    var body: some View {
        HStack {
            if message.isUser {
                Spacer()
            }
            
            VStack(alignment: message.isUser ? .trailing : .leading, spacing: 4) {
                Text(message.text)
                    .padding()
                    .background(message.isUser ? Color.blue : Color.gray.opacity(0.2))
                    .foregroundColor(message.isUser ? .white : .primary)
                    .cornerRadius(16)
                
                if let emotion = message.emotion, !message.isUser {
                    Text("Emoção: \(emotion)")
                        .font(.caption2)
                        .foregroundColor(.secondary)
                }
            }
            
            if !message.isUser {
                Spacer()
            }
        }
    }
}

struct ChatMessage: Identifiable {
    let id = UUID()
    let text: String
    let isUser: Bool
    let emotion: String?
}

