import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class q3 {

    public static void main(String[] args) {

        String url = "";
        String usuario = "";
        String senha = "";

        String sql = "SELECT * FROM alunos";

        int totalAlunos = 0;
        int aprovados = 0;
        int reprovados = 0;
        int recuperacao = 0;
        double somaMedia = 0;

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                var n1 = rs.getDouble("nota1");
                var n2 = rs.getDouble("nota2");
                var n3 = rs.getDouble("nota3");
                var n4 = rs.getDouble("nota4");

                double media = (n1 + n2 + n3 + n4) / 4;
                somaMedia += media;
                totalAlunos++;

                if (media >= 7) {
                    aprovados++;
                } else if (media < 3) {
                    reprovados++;
                } else {
                    recuperacao++;
                }
            }

            if (totalAlunos > 0) {
                double mediaGeral = somaMedia / totalAlunos;

                System.out.println("Número total de alunos: " + totalAlunos);
                System.out.println("Número de alunos aprovados: " + aprovados);
                System.out.println("Número de alunos em recuperação: " + recuperacao);
                System.out.println("Número de alunos reprovados: " + reprovados);
                System.out.println("Média geral da turma: " + mediaGeral);
            } else {
                System.out.println("Nenhum aluno cadastrado!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}