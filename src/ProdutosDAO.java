

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
    conn = new conectaDAO().connectDB();
    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.executeUpdate();
        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem.clear();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos no DAO: " + e.getMessage());
        }
        return listagem;
    }
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);
        prep.executeUpdate();
        
        javax.swing.JOptionPane.showMessageDialog(null, "Status do produto atualizado com sucesso!");
    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Erro ao vender: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
    conn = new conectaDAO().connectDB();
    
    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();
        listagem.clear(); 
        
        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            
            listagem.add(produto);
        }
    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage());
    }
    return listagem;
}
        
}

