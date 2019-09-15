package com.awmoney.api.exceptionhandler.model;

public class Erro {

	private String mensagemCliente;
	private String mensagemSistema;

	public Erro(String mensagemCliente, String mensagemSistema) {
		this.mensagemCliente = mensagemCliente;
		this.mensagemSistema = mensagemSistema;
	}

	public String getMensagemCliente() {
		return mensagemCliente;
	}

	public String getMensagemSistema() {
		return mensagemSistema;
	}
	
}
