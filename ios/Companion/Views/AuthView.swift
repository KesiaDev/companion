import SwiftUI

struct AuthView: View {
    @EnvironmentObject var authViewModel: AuthViewModel
    @State private var isLoginMode = true
    @State private var email = ""
    @State private var password = ""
    @State private var name = ""
    @State private var age = ""
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                Text("COMPANION")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                Text("companhia para o seu dia")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                
                Spacer()
                
                if isLoginMode {
                    loginView
                } else {
                    registerView
                }
                
                Spacer()
                
                Button(action: {
                    isLoginMode.toggle()
                }) {
                    Text(isLoginMode ? "Não tem conta? Cadastre-se" : "Já tem conta? Entrar")
                        .foregroundColor(.blue)
                }
            }
            .padding()
            .navigationTitle("")
            .navigationBarHidden(true)
        }
    }
    
    private var loginView: some View {
        VStack(spacing: 16) {
            TextField("Email", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .autocapitalization(.none)
                .keyboardType(.emailAddress)
            
            SecureField("Senha", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            if let error = authViewModel.errorMessage {
                Text(error)
                    .foregroundColor(.red)
                    .font(.caption)
            }
            
            Button(action: {
                Task {
                    await authViewModel.login(email: email, password: password)
                }
            }) {
                if authViewModel.isLoading {
                    ProgressView()
                } else {
                    Text("Entrar")
                        .frame(maxWidth: .infinity)
                }
            }
            .buttonStyle(.borderedProminent)
            .disabled(authViewModel.isLoading || email.isEmpty || password.isEmpty)
        }
    }
    
    private var registerView: some View {
        VStack(spacing: 16) {
            TextField("Nome (opcional)", text: $name)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Email", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .autocapitalization(.none)
                .keyboardType(.emailAddress)
            
            SecureField("Senha (mínimo 8 caracteres)", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Idade (opcional)", text: $age)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.numberPad)
            
            if let error = authViewModel.errorMessage {
                Text(error)
                    .foregroundColor(.red)
                    .font(.caption)
            }
            
            Button(action: {
                Task {
                    await authViewModel.register(
                        email: email,
                        password: password,
                        name: name.isEmpty ? nil : name,
                        age: Int(age)
                    )
                }
            }) {
                if authViewModel.isLoading {
                    ProgressView()
                } else {
                    Text("Cadastrar")
                        .frame(maxWidth: .infinity)
                }
            }
            .buttonStyle(.borderedProminent)
            .disabled(authViewModel.isLoading || email.isEmpty || password.count < 8)
        }
    }
}

