import java.sql.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hospital {

    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ================= MENU =================
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== SISTEMA HOSPITAL =====");
            System.out.println("1 - Sair");
            System.out.println("2 - Cadastrar médico");
            System.out.println("3 - Cadastrar paciente");
            System.out.println("4 - Buscar médico");
            System.out.println("5 - Buscar paciente");
            System.out.println("6 - Cadastrar consulta");
            System.out.println("7 - Remover consulta");
            System.out.println("8 - Atualizar horário consulta");
            System.out.println("9 - Relatório consultas");
            System.out.print("Opção: ");

            opcao = ler.nextInt();
            ler.nextLine();

            switch(opcao) {
                case 2 -> cadastrarMedico(ler);
                case 3 -> cadastrarPaciente(ler);
                case 4 -> buscarMedico(ler);
                case 5 -> buscarPaciente(ler);
                case 6 -> cadastrarConsulta(ler);
                case 7 -> removerConsulta(ler);
                case 8 -> atualizarConsulta(ler);
                case 9 -> relatorioConsultas();
            }

        } while(opcao != 1);
    }

    // ================= MÉDICO =================
    public static void cadastrarMedico(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Nome: ");
            String nome = ler.nextLine();
            System.out.print("Matrícula: ");
            int matricula = ler.nextInt(); ler.nextLine();
            System.out.print("Especialidade: ");
            String esp = ler.nextLine();
            System.out.print("Salário: ");
            double sal = ler.nextDouble();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO medico(nome, matricula, especialidade, salario) VALUES (?,?,?,?)");

            stmt.setString(1, nome);
            stmt.setInt(2, matricula);
            stmt.setString(3, esp);
            stmt.setDouble(4, sal);

            System.out.println(stmt.executeUpdate()>0 ? "Médico cadastrado" : "Erro");

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public static void buscarMedico(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Matrícula: ");
            int mat = ler.nextInt();

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM medico WHERE matricula=?");
            stmt.setInt(1, mat);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                System.out.println("Nome: "+rs.getString("nome"));
                System.out.println("Especialidade: "+rs.getString("especialidade"));
                System.out.println("Salário: "+rs.getDouble("salario"));
            } else System.out.println("Não encontrado");

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    // ================= PACIENTE =================
    public static void cadastrarPaciente(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Nome: ");
            String nome = ler.nextLine();
            System.out.print("CPF: ");
            String cpf = ler.nextLine();
            System.out.print("Doença: ");
            String d = ler.nextLine();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO paciente(nome, cpf, doenca) VALUES (?,?,?)");

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, d);

            System.out.println(stmt.executeUpdate()>0 ? "Paciente cadastrado" : "Erro");

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public static void buscarPaciente(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("CPF: ");
            String cpf = ler.nextLine();

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM paciente WHERE cpf=?");
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                System.out.println("Nome: "+rs.getString("nome"));
                System.out.println("Doença: "+rs.getString("doenca"));
            } else System.out.println("Não encontrado");

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    // ================= CONSULTA =================

    private static int getIdMedico(Connection conn, int matricula) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM medico WHERE matricula=?");
        stmt.setInt(1, matricula);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }

    private static int getIdPaciente(Connection conn, String cpf) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM paciente WHERE cpf=?");
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }

    public static void cadastrarConsulta(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Matrícula médico: ");
            int mat = ler.nextInt(); ler.nextLine();
            System.out.print("CPF paciente: ");
            String cpf = ler.nextLine();
            System.out.print("Horário (dd/MM/yyyy HH:mm): ");
            String data = ler.nextLine();
            System.out.print("Valor: ");
            double valor = ler.nextDouble();

            int idMed = getIdMedico(conn, mat);
            int idPac = getIdPaciente(conn, cpf);

            if(idMed==-1 || idPac==-1) {
                System.out.println("Médico ou paciente não existe");
                return;
            }

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.parse(data, f));

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO consulta(id_medico, id_paciente, horario, valor) VALUES (?,?,?,?)");

            stmt.setInt(1, idMed);
            stmt.setInt(2, idPac);
            stmt.setTimestamp(3, ts);
            stmt.setDouble(4, valor);

            System.out.println(stmt.executeUpdate()>0 ? "Consulta cadastrada" : "Erro");

        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public static void removerConsulta(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Matrícula médico: ");
            int mat = ler.nextInt(); ler.nextLine();
            System.out.print("CPF paciente: ");
            String cpf = ler.nextLine();
            System.out.print("Horário (dd/MM/yyyy HH:mm): ");
            String data = ler.nextLine();

            int idMed = getIdMedico(conn, mat);
            int idPac = getIdPaciente(conn, cpf);

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.parse(data, f));

            PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM consulta WHERE id_medico=? AND id_paciente=? AND horario=?");

            stmt.setInt(1, idMed);
            stmt.setInt(2, idPac);
            stmt.setTimestamp(3, ts);

            System.out.println(stmt.executeUpdate()>0 ? "Removida" : "Não encontrada");

        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public static void atualizarConsulta(Scanner ler) {
        try (Connection conn = conectar()) {
            System.out.print("Matrícula médico: ");
            int mat = ler.nextInt(); ler.nextLine();
            System.out.print("CPF paciente: ");
            String cpf = ler.nextLine();
            System.out.print("Horário antigo (dd/MM/yyyy HH:mm): ");
            String antigo = ler.nextLine();
            System.out.print("Novo horário (dd/MM/yyyy HH:mm): ");
            String novo = ler.nextLine();

            int idMed = getIdMedico(conn, mat);
            int idPac = getIdPaciente(conn, cpf);

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Timestamp tsAntigo = Timestamp.valueOf(LocalDateTime.parse(antigo, f));
            Timestamp tsNovo = Timestamp.valueOf(LocalDateTime.parse(novo, f));

            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE consulta SET horario=? WHERE id_medico=? AND id_paciente=? AND horario=?");

            stmt.setTimestamp(1, tsNovo);
            stmt.setInt(2, idMed);
            stmt.setInt(3, idPac);
            stmt.setTimestamp(4, tsAntigo);

            System.out.println(stmt.executeUpdate()>0 ? "Atualizado" : "Não encontrado");

        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public static void relatorioConsultas() {
        String sql = """
            SELECT c.horario, c.valor,
                   m.nome nome_medico, m.matricula,
                   p.nome nome_paciente, p.cpf
            FROM consulta c
            JOIN medico m ON c.id_medico=m.id
            JOIN paciente p ON c.id_paciente=p.id
            ORDER BY c.horario
        """;

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            int i=1;

            while(rs.next()) {
                System.out.println("\nConsulta "+i++);
                System.out.println("Médico: "+rs.getString("nome_medico")+" ("+rs.getInt("matricula")+")");
                System.out.println("Paciente: "+rs.getString("nome_paciente")+" ("+rs.getString("cpf")+")");
                System.out.println("Horário: "+rs.getTimestamp("horario").toLocalDateTime().format(f));
                System.out.println("Valor: R$ "+rs.getDouble("valor"));
            }

            if(i==1) System.out.println("Nenhuma consulta cadastrada.");

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
}
