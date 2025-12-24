import SwiftUI

struct OnboardingFlowView: View {
    @State private var currentStep = 0
    @State private var companionType: String?
    @State private var conversationTone: String?
    
    var body: some View {
        NavigationView {
            Group {
                switch currentStep {
                case 0:
                    AgeVerificationView(onVerified: { currentStep = 1 })
                case 1:
                    CompanionTypeView(
                        selectedType: $companionType,
                        onNext: { currentStep = 2 }
                    )
                case 2:
                    ConversationToneView(
                        selectedTone: $conversationTone,
                        onNext: { currentStep = 3 }
                    )
                case 3:
                    AvatarCreationView(
                        companionType: companionType ?? "FRIEND",
                        conversationTone: conversationTone ?? "Calmo",
                        onComplete: { currentStep = 4 }
                    )
                default:
                    ChatView()
                }
            }
        }
    }
}

struct AgeVerificationView: View {
    let onVerified: () -> Void
    @State private var ageConfirmed = false
    
    var body: some View {
        VStack(spacing: 24) {
            Text("Confirmação de Idade")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            Text("O COMPANION é um aplicativo para pessoas de 15 anos ou mais.")
                .multilineTextAlignment(.center)
            
            Text("Você confirma que tem 15 anos ou mais?")
                .fontWeight(.medium)
                .multilineTextAlignment(.center)
            
            HStack(spacing: 16) {
                Button("Não") {
                    // Fechar app ou mostrar mensagem
                }
                .buttonStyle(.bordered)
                
                Button("Sim") {
                    ageConfirmed = true
                }
                .buttonStyle(.borderedProminent)
            }
            
            if ageConfirmed {
                Button("Continuar", action: onVerified)
                    .buttonStyle(.borderedProminent)
            }
            
            Spacer()
        }
        .padding()
    }
}

struct CompanionTypeView: View {
    @Binding var selectedType: String?
    let onNext: () -> Void
    
    let types = [
        ("FRIEND", "Amigo(a)", "Uma presença amigável e acolhedora"),
        ("CONFIDANT", "Confidente", "Alguém para compartilhar pensamentos e sentimentos"),
        ("NEUTRAL", "Companheiro emocional neutro", "Presença calma e neutra para o seu dia")
    ]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Escolha o tipo de companhia")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            Text("Como você gostaria que eu te acompanhasse?")
                .foregroundColor(.secondary)
            
            ForEach(types, id: \.0) { type in
                Button(action: {
                    selectedType = type.0
                }) {
                    HStack {
                        VStack(alignment: .leading) {
                            Text(type.1)
                                .fontWeight(.bold)
                            Text(type.2)
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        Spacer()
                        if selectedType == type.0 {
                            Image(systemName: "checkmark.circle.fill")
                                .foregroundColor(.blue)
                        }
                    }
                    .padding()
                    .background(selectedType == type.0 ? Color.blue.opacity(0.1) : Color.gray.opacity(0.1))
                    .cornerRadius(12)
                }
            }
            
            Spacer()
            
            Button("Continuar", action: onNext)
                .buttonStyle(.borderedProminent)
                .frame(maxWidth: .infinity)
                .disabled(selectedType == nil)
        }
        .padding()
    }
}

struct ConversationToneView: View {
    @Binding var selectedTone: String?
    let onNext: () -> Void
    
    let tones = [
        ("Calmo", "Conversas serenas e tranquilas"),
        ("Animado", "Presença mais energética e positiva"),
        ("Neutro", "Tom equilibrado e natural"),
        ("Empático", "Foco em compreensão e apoio emocional")
    ]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Escolha o tom de conversa")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            Text("Como você prefere que eu me comunique?")
                .foregroundColor(.secondary)
            
            ForEach(tones, id: \.0) { tone in
                Button(action: {
                    selectedTone = tone.0
                }) {
                    HStack {
                        VStack(alignment: .leading) {
                            Text(tone.0)
                                .fontWeight(.bold)
                            Text(tone.1)
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        Spacer()
                        if selectedTone == tone.0 {
                            Image(systemName: "checkmark.circle.fill")
                                .foregroundColor(.blue)
                        }
                    }
                    .padding()
                    .background(selectedTone == tone.0 ? Color.blue.opacity(0.1) : Color.gray.opacity(0.1))
                    .cornerRadius(12)
                }
            }
            
            Spacer()
            
            Button("Continuar", action: onNext)
                .buttonStyle(.borderedProminent)
                .frame(maxWidth: .infinity)
                .disabled(selectedTone == nil)
        }
        .padding()
    }
}

struct AvatarCreationView: View {
    let companionType: String
    let conversationTone: String
    let onComplete: () -> Void
    
    @State private var avatarName = ""
    @State private var selectedStyle = ""
    @State private var selectedBodyType = ""
    @State private var selectedFaceType = ""
    @State private var selectedHair = ""
    @State private var selectedSkinTone = ""
    @State private var pronouns = ""
    @State private var isLoading = false
    
    let styles = ["Casual", "Formal", "Relaxado", "Moderno"]
    let bodyTypes = ["Médio", "Esguio", "Robusto"]
    let faceTypes = ["Redondo", "Oval", "Quadrado", "Alongado"]
    let hairOptions = ["Curto", "Médio", "Longo", "Sem cabelo"]
    let skinTones = ["Claro", "Médio", "Escuro", "Muito escuro"]
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 20) {
                Text("Crie seu Companion")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                Text("Personalize o avatar do seu companheiro")
                    .foregroundColor(.secondary)
                
                TextField("Nome do avatar", text: $avatarName)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                
                TextField("Pronomes (opcional)", text: $pronouns)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                
                // Adicione mais campos de seleção aqui...
                // (Simplificado para o exemplo)
                
                Button(action: {
                    // TODO: Salvar avatar via API
                    onComplete()
                }) {
                    if isLoading {
                        ProgressView()
                    } else {
                        Text("Criar Companion")
                            .frame(maxWidth: .infinity)
                    }
                }
                .buttonStyle(.borderedProminent)
                .disabled(avatarName.isEmpty || isLoading)
            }
            .padding()
        }
    }
}

