package semAglomerar.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import semAglomerar.ConnectionBD.ConnectionMySql;
import semAglomerar.Model.Login;
import semAglomerar.Model.Loja;
import semAglomerar.Model.Responsavel;
import semAglomerar.Model.Shopping;

public class LojaDAO {

    private Loja loja;
    private Shopping shop;
    private Responsavel resp;
    private Login login;

    public List<Loja> findAll() throws SQLException {

        String sql = "SELECT loja_nome, loja_razao, loja_cnpj, loja_localiza, loja_categoria, resp_nome, resp_cpf, resp_email, resp_telefone, shop_nome, shop_cnpj, login_usuario"
                + " FROM  Loja, 	Responsavel,	Login,    Shopping "
                + " WHERE loja_resp_id=resp_id AND loja_login_id=login_id AND loja_shop_id=shop_id; ";

        List<Loja> resul = new ArrayList<>();

        try (Connection conn = ConnectionMySql.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                loja.setId(rs.getInt("loja_id"));
                loja.setNome(rs.getString("loja_nome"));
                loja.setRazaoSocial(rs.getString("loja_razao"));
                loja.setCnpj(rs.getString("loja_cnpj"));
                loja.setLocalizacao(rs.getString("loja_localiza"));
                loja.setCategoria(rs.getString("loja_categoria"));

                resp.setId(rs.getInt("resp_id"));
                resp.setNome(rs.getString("resp_nome"));
                resp.setCpf(rs.getString("resp_cpf"));
                resp.setEmail(rs.getString("resp_email"));
                resp.setTelefone(rs.getString("resp_telefone"));

                shop.setId(rs.getInt("shop_id"));
                shop.setNome(rs.getString("shop_nome"));
                shop.setCnpj(rs.getString("shop_cnpj"));

                login.setId(rs.getInt("login_id"));
                login.setUsuario(rs.getString("login_usuario"));

                resul.add(loja);
            }
        }
        return resul;
    }

    public Loja findByCnpj(String cnpj) throws SQLException {

        String sql = "SELECT loja_id, loja_nome, loja_razao, loja_cnpj, loja_localiza, loja_categoria, resp_id, resp_nome, resp_cpf, resp_email, resp_telefone, shop_id, shop_nome, shop_cnpj, login_id, login_usuario, login_senha"
                + " FROM  Loja, 	Responsavel,	Login,    Shopping "
                + " WHERE loja_cnpj= ? "
                + " AND loja_resp_id=resp_id AND loja_login_id=login_id AND loja_shop_id=shop_id; ";

        try (Connection conn = ConnectionMySql.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    loja.setId(rs.getInt("loja_id"));
                    loja.setNome(rs.getString("loja_nome"));
                    loja.setRazaoSocial(rs.getString("loja_razao"));
                    loja.setCnpj(rs.getString("loja_cnpj"));
                    loja.setLocalizacao(rs.getString("loja_localiza"));
                    loja.setCategoria(rs.getString("loja_categoria"));

                    resp.setId(rs.getInt("resp_id"));
                    resp.setNome(rs.getString("resp_nome"));
                    resp.setCpf(rs.getString("resp_cpf"));
                    resp.setEmail(rs.getString("resp_email"));
                    resp.setTelefone(rs.getString("resp_telefone"));

                    shop.setId(rs.getInt("shop_id"));
                    shop.setNome(rs.getString("shop_nome"));
                    shop.setCnpj(rs.getString("shop_cnpj"));

                    login.setId(rs.getInt("login_id"));
                    login.setUsuario(rs.getString("login_usuario"));
                    login.setHashSenha(rs.getString("login_senha"));
                    return loja;
                }
            } catch (Exception e) {
                System.out.print("Erro ao pesquisar o CNPJ: " + cnpj);
            }
        }
        return null;
    }
    
    public Loja findById(int id) throws SQLException {

        String sql = "SELECT loja_id, loja_nome, loja_cnpj, loja_razao, loja_localiza, loja_categoria "
                + "FROM Loja "
                + "WHERE loja_id = ?;";

        Connection conn = null;

        try {
            conn = ConnectionMySql.obterConexao();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(id));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loja = new Loja();
                loja.setId(rs.getInt("loja_id"));
                loja.setNome(rs.getString("loja_nome"));
                loja.setRazaoSocial(rs.getString("loja_razao"));
                loja.setCnpj(rs.getString("loja_cnpj"));
                loja.setLocalizacao(rs.getString("loja_localiza"));
                loja.setCategoria(rs.getString("loja_categoria"));
                return loja;
            }
        } catch (SQLException e) {
            conn.close();
        }

        return null;
    }
    
    public Loja findByUser(String usuario) throws SQLException {

        String sql = "SELECT loja_id, loja_nome, loja_cnpj, loja_razao, loja_localiza, loja_categoria "
                + " FROM Loja, Responsavel,Login "
                + " WHERE loja_resp_id=resp_id AND loja_login_id = login_id AND login_usuario= ?";

        Connection conn = null;

        try {
            conn = ConnectionMySql.obterConexao();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loja = new Loja();
                loja.setId(rs.getInt("loja_id"));
                loja.setNome(rs.getString("loja_nome"));
                loja.setRazaoSocial(rs.getString("loja_razao"));
                loja.setCnpj(rs.getString("loja_cnpj"));
                loja.setLocalizacao(rs.getString("loja_localiza"));
                loja.setCategoria(rs.getString("loja_categoria"));
                return loja;
            }
        } catch (SQLException e) {
            conn.close();
        }

        return null;
    }

    public List<Loja> findByShopId(int shop_id) throws SQLException {
        List<Loja> lojas = new ArrayList<>();
        
        Connection conn = null;
        try {
            String sql = "SELECT loja_id, loja_nome, loja_cnpj, loja_razao, loja_localiza, loja_categoria "
                    + " FROM Loja "
                    + " WHERE loja_status <> 'Inativo' AND loja_shop_id = ?;";

            conn = ConnectionMySql.obterConexao();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(shop_id));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loja = new Loja();
                loja.setId(rs.getInt("loja_id"));
                loja.setNome(rs.getString("loja_nome"));
                loja.setRazaoSocial(rs.getString("loja_razao"));
                loja.setCnpj(rs.getString("loja_cnpj"));
                loja.setLocalizacao(rs.getString("loja_localiza"));
                loja.setCategoria(rs.getString("loja_categoria"));
                lojas.add(loja);
            }
        } catch (SQLException e) {
            conn.close();
        }

        return lojas;
    }

    public Loja Pesquisa(String pesq) throws SQLException {
        String sql = "SELECT loja_id, loja_nome, loja_cnpj, loja_razao, loja_localiza, loja_categoria "
                + "FROM loja "
                + "WHERE loja_status <> 'Inativo' AND loja_nome like ? ;";

        Connection conn = null;

        try {
            conn = ConnectionMySql.obterConexao();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + pesq + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loja.setId(rs.getInt("loja_id"));
                loja.setNome(rs.getString("loja_nome"));
                loja.setRazaoSocial(rs.getString("loja_razao"));
                loja.setCnpj(rs.getString("loja_cnpj"));
                loja.setLocalizacao(rs.getString("loja_localiza"));
                loja.setCategoria(rs.getString("loja_categoria"));
                return loja;
            }
        } catch (SQLException e) {
            conn.rollback();
        }

        return loja;
    }

    public void inserirLoja(Loja loja, Login login, Responsavel resp, Shopping shop) throws SQLException {
        String sql = "INSERT INTO Loja (loja_nome, loja_cnpj, loja_localiza, loja_razao, loja_categoria, loja_logo, loja_shop_id, loja_resp_id, loja_login_id) "
                + " VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = null;

        try {
            conn = ConnectionMySql.obterConexao();
            // DESLIGAR AUTO-COMMIT -> POSSIBILITAR DESFAZER OPERACOES EM CASOS DE ERROS
            conn.setAutoCommit(false);

            // ADICIONAR O Statement.RETURN_GENERATED_KEYS PARA RECUPERAR O ID GERADO NO BD
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, loja.getNome());
            stmt.setString(2, loja.getCnpj());
            stmt.setString(3, loja.getLocalizacao());
            stmt.setString(4, loja.getRazaoSocial());
            stmt.setString(5, loja.getCategoria());
            stmt.setString(6, "n");
            stmt.setInt(7, shop.getId());
            stmt.setInt(8, resp.getId());
            stmt.setInt(9, login.getId());
            boolean resul = stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys(); // RECUPERA O ID GERADO PARA O INFO NOVO
            while (rs.next()) {
                Integer idGerado = rs.getInt(1);
                login.setId(idGerado);
            }

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
        }
    }

    public void atualizarLoja(Loja loja) throws SQLException {
        String sql = "UPDATE Loja set loja_nome=?, loja_cnpj=?, loja_localiza=?,loja_razao=?, loja_categoria=? WHERE loja_id=?";
        try (Connection conn = ConnectionMySql.obterConexao()) {
            // DESLIGAR AUTO-COMMIT -> POSSIBILITAR DESFAZER OPERACOES EM CASOS DE ERROS
            conn.setAutoCommit(false);
            // ADICIONAR O Statement.RETURN_GENERATED_KEYS PARA RECUPERAR O ID GERADO NO BD
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, loja.getNome());
                stmt.setString(2, loja.getCnpj());
                stmt.setString(3, loja.getLocalizacao());
                stmt.setString(4, loja.getRazaoSocial());
                stmt.setString(5, loja.getCategoria());
                stmt.setInt(6, loja.getId());
                int resul = stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    // RECUPERA O ID GERADO PARA O INFO NOVO
                    while (rs.next()) {
                        Integer idGerado = rs.getInt(6);
                        loja.setId(idGerado);
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void deletarLoja(Loja loja) throws SQLException {
        String sql = "UPDATE Loja SET loja_status = 'Inativo' WHERE loja_id=?";
        try (Connection conn = ConnectionMySql.obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, loja.getId());
                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
