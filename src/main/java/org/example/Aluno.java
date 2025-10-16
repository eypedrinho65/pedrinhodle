package org.example;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//XmlType define a ordem dos elementos no XML gerado
@XmlType(propOrder = {"nome", "notas"})
public class Aluno {

    private String nome;
    private List<Double> notas = new ArrayList<>();

    //construtor vazio é obrigatório para a JAXB
    public Aluno() {}

    public Aluno(String nome) {this.nome = nome;}

    @XmlElement
    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    //@XmlElementWrapper cria a tag <notas> para agrupar as notas
    //@XmlElement (name = "nota") define o nome de cada item da lista
    @XmlElementWrapper(name = "notas")
    @XmlElement(name = "nota")
    public List<Double> getNotas() {return notas;}

    public void setNotas(List<Double> notas) {this.notas = notas;}

    // --- Metodos Auxiliares ---
    public void adicionarNota(double nota) {this.notas.add(nota);}

    public double calcularMedia() {
        if (notas == null || notas.isEmpty()) {
            return 0.0;
        }
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }


    public String toSring() {
        String notasFormatadas = notas.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return String.format("Aluno: %-20s | Notas: [%s] | Média: %2.f",
                nome, notasFormatadas, calcularMedia());
    }
}