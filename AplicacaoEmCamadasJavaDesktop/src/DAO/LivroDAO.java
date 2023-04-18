/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DTO.Livro;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private GerenciadorBD bd;

    public LivroDAO() {
        bd = new GerenciadorBD();
    }

    public void cadastrar(Livro livro) throws SQLException {
        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "INSERT INTO livro (titulo, autor, editora, ano_publicacao, num_paginas, isbn) VALUES (?, ?, ?, ?, ?, ?)")
            ) 
        {
            comando.setString(1, livro.getTitulo());
            comando.setString(2, livro.getAutor());
            comando.setString(3, livro.getEditora());
            comando.setInt(4, livro.getAno());
            comando.setLong(5, livro.getPaginas());
            comando.setString(6, livro.getIsbn());

            comando.executeUpdate();
        }
    }

    public List<Livro> listar() throws SQLException {

        List<Livro> listaDeLivro = new ArrayList<>();

        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "SELECT ID, titulo, autor, editora, ano_publicacao, num_paginas, isbn FROM livro");
             ResultSet tabela = comando.executeQuery()
            ) 
        {
            while (tabela.next()) {
                Livro livro = new Livro();

                livro.setId(tabela.getInt("id"));
                livro.setTitulo(tabela.getString("titulo"));
                livro.setAutor(tabela.getString("autor"));
                livro.setEditora(tabela.getString("editora"));
                livro.setAno(tabela.getInt("ano_publicacao"));
                livro.setPaginas(tabela.getInt("num_paginas"));
                livro.setIsbn(tabela.getString("isbn"));
                
                listaDeLivro.add(livro);
            }
        }

        return listaDeLivro;
    }

    /*public Livro pesquisar(Livro livro) throws SQLException {

        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "SELECT titulo, ano, paginas, autores, preco FROM livro WHERE id = ?")
            ) 
        {
            comando.setInt(1, livro.getId());
            ResultSet tabela = comando.executeQuery();

            boolean existeLivro = false;
            
            if (tabela.next()) {
            
                livro.setId(tabela.getInt("id"));
                livro.setTitulo(tabela.getString("titulo"));
                livro.setAno(tabela.getInt("ano"));
                livro.setPaginas(tabela.getInt("paginas"));
                livro.setAutores(tabela.getString("autores"));
                livro.setPreco(tabela.getDouble("preco"));
                
                existeLivro = true;
            }
            
            if(!existeLivro)
            {
                livro.setId(0);
            }
        }
       
        return livro;
    }*/

    public void alterar(Livro livro) throws SQLException {
        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "UPDATE livro SET titulo = ?, autor = ?, editora = ?, ano_publicacao = ?, num_paginas = ?, isbn = ? WHERE id = ?")
            ) 
        {
            comando.setString(1, livro.getTitulo());
            comando.setString(2, livro.getAutor());
            comando.setString(3, livro.getEditora());
            comando.setInt(4, livro.getAno());
            comando.setInt(5, livro.getPaginas());
            comando.setString(6, livro.getIsbn());

            comando.executeUpdate();
        }
    }

    public void excluir(Livro livro) throws SQLException {
        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "DELETE FROM livro WHERE id = ?")
            ) 
        {
            comando.setInt(1, livro.getId());

            comando.executeUpdate();
        }
    }
}
