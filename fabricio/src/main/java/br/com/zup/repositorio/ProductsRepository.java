package br.com.zup.repositorio;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.zup.entidade.ProductsEntity;

public class ProductsRepository {
	
	
	private final EntityManagerFactory entityManagerFactory;
	 
	private final EntityManager entityManager;
 
	public ProductsRepository(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("persistence_db_zup");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
	
	
	/**
	 * RETORNA TODOS OS PRODUTOS CADASTRADOS NO BANCO DE DADOS 
	 * */
	@SuppressWarnings("unchecked")
	public List<ProductsEntity> todosProdutos(){
 
		return this.entityManager.createQuery("from ProductsEntity obj ORDER BY obj.name").getResultList();
	}
 
	/**
	 * CRIA UM NOVO PRODUTO NO BANCO DE DADOS
	 * */
	public void salvar(ProductsEntity productsEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(productsEntity);
		this.entityManager.getTransaction().commit();
	}
 
	/**
	 * ALTERA UM REGISTRO CADASTRADO
	 * */
	public void alterar(ProductsEntity productsEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(productsEntity);
		this.entityManager.getTransaction().commit();
	}
 
	
 
	/**
	 * CONSULTA UM PRODUTO CADASTRO PELO ID
	 * */
	public ProductsEntity getProduto(Integer id){
 
		return this.entityManager.find(ProductsEntity.class, id);
	}
 
	/**
	 * EXCLUINDO UM REGISTRO PELO ID
	**/
	public void excluir(Integer id){
 
		ProductsEntity produto = this.getProduto(id);
 
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(produto);
		this.entityManager.getTransaction().commit();
 
	}

}
