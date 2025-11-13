import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GerenciarUsuario {

    static final String URL ="";
    static final String USER = "";
    static final String PASSWORD = "";

    static Scanner ler = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("=== MENU USUÁRIO ===");
            System.out.println("1. Inserir usuário");
            System.out.println("2. Atualizar dado");
            System.out.println("3. Deletar usuário");
            System.out.println("4. Listar todos usuários");
            System.out.println("5. Buscar usuário por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = ler.nextInt();
            ler.nextLine();

            switch (opcao) {
                case 1:
                    inserirUsuario();
                    break;
                case 2:
                    atualizarUsuario();
                    break;
                case 3:
                    deletarUsuario();
                    break;
                case 4:
                    listarUsuarios();
                    break;
                case 5:
                    buscarPorId();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

        ler.close();
    }

    // ================== INSERIR USUÁRIO ==================
    static void inserirUsuario() {
        System.out.print("Digite o nome: ");
        String nome = ler.nextLine();

        String sexo;
        while (true) {
            System.out.print("Digite o sexo (M/F): ");
            sexo = ler.nextLine().toUpperCase();

            if (sexo.equals("M") || sexo.equals("F")) {
                break;
            } else {
                System.out.println("Sexo inválido! Digite apenas 'M' ou 'F'.");
            }
        }

        System.out.print("Digite a idade: ");
        int idade = ler.nextInt();
        ler.nextLine();

        String sql = "INSERT INTO usuario (nome, sexo, idade) VALUES (?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, sexo);
            stmt.setInt(3, idade);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas + " usuário inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário!");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }

    // ================== ATUALIZAR USUÁRIO ==================
    static void atualizarUsuario() {
        System.out.print("Digite o ID do usuário que deseja atualizar: ");
        int id = ler.nextInt();
        ler.nextLine();

        System.out.print("Digite o novo nome: ");
        String novoNome = ler.nextLine();

        String novoSexo;
        while (true) {
            System.out.print("Digite o novo sexo (M/F): ");
            novoSexo = ler.nextLine().toUpperCase();

            if (novoSexo.equals("M") || novoSexo.equals("F")) {
                break;
            } else {
                System.out.println("Sexo inválido! Digite apenas 'M' ou 'F'.");
            }
        }

        System.out.print("Digite a nova idade: ");
        int novaIdade = ler.nextInt();
        ler.nextLine();

        String sql = "UPDATE usuario SET nome = ?, sexo = ?, idade = ? WHERE id = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setString(2, novoSexo);
            stmt.setInt(3, novaIdade);
            stmt.setInt(4, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário!");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }

    // ================== DELETAR USUÁRIO ==================
    static void deletarUsuario() {
        System.out.print("Digite o ID do usuário que deseja deletar: ");
        int id = ler.nextInt();
        ler.nextLine();

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário!");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }

    // ================== LISTAR TODOS ==================
    static void listarUsuarios() {
        String sql = "SELECT * FROM usuario";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("=== LISTA DE USUÁRIOS ===");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Idade: " + rs.getInt("idade"));
                System.out.println("-------------------------");
            }

            if (!encontrou) {
                System.out.println("Nenhum usuário cadastrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários!");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }

    // ================== BUSCAR POR ID ==================
    static void buscarPorId() {
        System.out.print("Digite o ID do usuário: ");
        int id = ler.nextInt();
        ler.nextLine();

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("=== DADOS DO USUÁRIO ===");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Sexo: " + rs.getString("sexo"));
                    System.out.println("Idade: " + rs.getInt("idade"));
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário!");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }
}
