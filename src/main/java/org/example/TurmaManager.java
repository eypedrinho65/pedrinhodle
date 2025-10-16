package org.example;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TurmaManager {

    private final String nomeArquivo;

    public TurmaManager(String nomeArquivo) {this.nomeArquivo = nomeArquivo;}

    public Turma carregar() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return new Turma(); // Retorna objeto novo se o arquivo não existe
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Turma.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Turma) unmarshaller.unmarshal(arquivo);
        } catch (JAXBException e) {
            System.err.println("Erro ao carregar o arquivo XML: " + e.getMessage());
            e.printStackTrace();
            return new Turma(); // Retorna objeto vazio em caso de erro
        }
    }

    public void salvar(Turma turma) {
        try {
            JAXBContext context = JAXBContext.newInstance(Turma.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(turma, new File(nomeArquivo));
        } catch (JAXBException e) {
            System.err.println("Erro ao salvar o arquivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @XmlRootElement(name = "turma")
    public static class Turma {

        private List<Aluno> alunos = new ArrayList<>();

        @XmlElementWrapper(name = "alunos")
        @XmlElement(name = "aluno")
        public List<Aluno> getAlunos() {return alunos;}

        public void setAlunos(List<Aluno> alunos) {this.alunos = alunos;}
    }
}