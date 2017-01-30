package br.com.zup.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.zup.entidade.ProductsEntity;
import br.com.zup.http.Products;
import br.com.zup.repositorio.ProductsRepository;
 
 
 
/**
 * Essa classe vai expor os nossos métodos para serem acessasdos via http
 * 
 * @Path - Caminho para a chamada da classe que vai representar o nosso serviço
 **/
@Path("/service")
public class ServiceController {
	
	private final  ProductsRepository repository = new ProductsRepository();
	 
	/**
	 * @Consumes - Usado para dizer em quais formatos os parâmetros poderão ser passados para o serviço. 
	 * @Produces - Usado para dizer em quais os formatos que o serviço rest irá retornar.
	 **/
	
	
	
	/**
	 * Esse método lista todos produtos cadastradas no banco de dados
	 * */
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/todosProdutos")
	public List<Products> todosProdutos(){
 
		List<Products> produto =  new ArrayList<Products>();
 
		List<ProductsEntity> listaEntityProducts = repository.todosProdutos();
 
		for (ProductsEntity entity : listaEntityProducts) {
 
			produto.add(new Products(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategory()));
		}
 
		return produto;
	}

	
	/**
	 * Esse método busca um produto cadastrada pelo id
	 * */
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/getProducts/{id}")
	public Products getProducts(@PathParam("id") Integer id){
 
		ProductsEntity entity = repository.getProduto(id);
 
		if(entity != null)
			return new Products(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategory());
 
		return null;
	}

	
	
	
	/**
	 * 
	 * Esse método cadastra um novo produto
	 **/
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/cadastrar")
	public String cadastrar(Products produto){
 
		ProductsEntity entity = new ProductsEntity();
 
		try {
 
			entity.setName(produto.getName());
			entity.setDescription(produto.getDescription());
			entity.setPrice(produto.getPrice());
			entity.setCategory(produto.getCategory());
			
			
 
			repository.salvar(entity);
 
			return "Produto cadastrado com sucesso!";
 
		} catch (Exception e) {
 
			return "Erro ao cadastrar o Produto! " + e.getMessage();
		}
 
	}
 
	/**
	 * Essse método altera um produto já cadastrado no banco de dados
	 **/
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar/{id}")	
	public String alterar(Products produto, @PathParam("id") Integer id){
 
		try {
 
			ProductsEntity entity = repository.getProduto(id);
 
			entity.setName(produto.getName());
			entity.setDescription(produto.getDescription());
			entity.setPrice(produto.getPrice());
			entity.setCategory(produto.getCategory());
			
			repository.alterar(entity);
 
			return "Produto alterado com sucesso!";

 
		} catch (Exception e) {
 
			return "Erro ao alterar o Produto! " + e.getMessage();
		}
 
	}
	 
	 
	/**
	 * Excluindo um produto pelo id
	 * */
	@DELETE
	@Produces("application/json; charset=UTF-8")
	@Path("/excluir/{id}")	
	public String excluir(@PathParam("id") Integer id){
 
		try {
 
			repository.excluir(id);
 
			return "Produto excluido com sucesso!";
 
		} catch (Exception e) {
 
			return "Erro ao excluir o Produto! " + e.getMessage();
		}
 
	}

}
