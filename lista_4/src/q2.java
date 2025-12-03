import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class q2 {

    public static void main(String[] args) {

        String url = "";
        String usuario = "";
        String senha = "";

        String sql = "select * from alunos";

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {  
                System.out.println("Nenhum aluno cadastrado!");
                return;
            }

            while (rs.next()) {
                var numero = rs.getInt("numero");
                var nome = rs.getString("nome");
                var curso = rs.getString("curso");
                var n1 = rs.getDouble("nota1");
                var n2 = rs.getDouble("nota2");
                var n3 = rs.getDouble("nota3");
                var n4 = rs.getDouble("nota4");

                var media = (n1 + n2 + n3 + n4) / 4;

                String situacao;

                if (media >= 7) {
                    situacao = "Aprovado";
                } else if (media < 3) {
                    situacao = "Reprovado";
                } else {
                    situacao = "Recuperação";
                }

                System.out.println("Aluno " + numero + ": " + nome);
                System.out.println("Curso: " + curso);
                System.out.println("Notas: " + n1 + " " + n2 + " " + n3 + " " + n4);
                System.out.println("Situação: " + situacao + " com média " + media);
                System.out.println("-------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}