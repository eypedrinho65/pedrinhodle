package org.example;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class MainAlunos {
    private static final TurmaManager manager = new TurmaManager("turma.xml");
    private static final Scanner scanner = new Scanner(System.in);
    private static TurmaManager.Turma turma;

    public static void main(String[] args) {
        turma = manager.carregar();
        System.out.println("Sistema de Gerenciamento de Alunos e Notas");
        System.out.println(turma.getAlunos().size() + " aluno(s) carregado(s).");

        int opcao = 0;
        while (opcao != 5) {
            exibirMenu();
            try{
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpa o buffer
                switch (opcao) {
                    case 1: adicionarAluno(); break;
                    case 2: listarAlunos(); break;
                    case 3: adicionarNotaAluno(); break;
                    case 4: removerAluno(); break;
                    case 5: System.out.println("Salvando dados e encerrando..."); break;
                    default: System.out.println("Opção invalida!"); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada invalida. Por favor, digite um numero.");
                scanner.nextLine(); //Limpa o buffer em caso de erro de letra
            }
        }
        manager.salvar(turma);
        System.out.println("Dados salvos com sucesso em 'turma.xml'.");
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Adicionar Aluno");
        System.out.println("2. Listar Alunos e Médias");
        System.out.println("3. Adicionar Nota a um Aluno");
        System.out.println("4. Remover aluno");
        System.out.println("5. Sair e Salvar");
        System.out.println("Escolha uma opção: ");
    }
    private static void adicionarAluno() {
        System.out.println("\n--- Adicionar novo Aluno ---");
        System.out.println("Nome do Aluno: ");
        String nome = scanner.nextLine();

        if (turma.getAlunos().stream().anyMatch(a -> a.getNome().equalsIgnoreCase(nome))) {
            System.out.println("Erro: Já existe um aluno com este nome.");
            return;
        }

        Aluno novoAluno = new Aluno(nome);
        System.out.println("Digite as notas. Digite -1 para terminar");
        double nota = 0;
        while (true) {
            System.out.println("Nota: ");
            try{
                nota = scanner.nextDouble();
                if (nota == -1) {
                    break;
                }
                if (nota >= 0 && nota <10) {
                    novoAluno.adicionarNota(nota);
                } else {
                    System.out.println("Nota inválida. Deve ser entre 0 e 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); //Descarta a entrada incorreta
            }
        }
        scanner.nextLine(); //Limpa o buffer
        turma.getAlunos().add(novoAluno);
        System.out.println("Aluno " + nome + " adicionado com sucesso!");
    }

    private static void listarAlunos() {
        System.out.println("\n--- Lista de Alunos na turma ---");
        if (turma.getAlunos().isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            turma.getAlunos().forEach(System.out::println);
        }
    }

    private static void adicionarNotaAluno() {
        System.out.println("\n--- Adicionar nota a um aluno ---");
        System.out.println("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        Optional<Aluno> alunoOpt = turma.getAlunos().stream()
                .filter(a-> a.getNome().equalsIgnoreCase(nome))
                .findFirst();

        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            System.out.println("Digite a nova nota para " + aluno.getNome() + ": ");
            try {
                double novaNota = scanner.nextDouble();
                scanner.nextLine(); // limpa o buffer
                if (novaNota >= 0 && novaNota <=10) {
                    aluno.adicionarNota(novaNota);
                    System.out.println("Nota adicionada com sucesso!");
                } else {
                    System.out.println("Nota invalida. Deve ser entre 0 e 10.");
                }
            } catch (InputMismatchException e){
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); //Limpa o buffer
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void removerAluno() {
        System.out.println("\n--- Remover Aluno ---");
        System.out.println("Digite o nome exato do aluno a ser removido: ");
        String nome = scanner.nextLine();

        boolean removido = turma.getAlunos().removeIf(a -> a.getNome().equalsIgnoreCase(nome));

        if(removido) {
            System.out.println("Aluno removido com sucesso!");
        } else {
            System.out.println("Aluno não encontrado");
        }
    }
}