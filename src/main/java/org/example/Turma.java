package org.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "turma") // 8 usages
public class Turma {

    private List<Aluno> alunos = new ArrayList<>(); // 2 usages

    @XmlElementWrapper(name = "alunos") // 7 usages
    @XmlElement(name = "aluno")
    public List<Aluno> getAlunos() { return alunos; }

    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }
}