package Utilitario;
import java.util.Scanner;
import static Utilitario.Utilitario.*;

public class ValidarEndereco {
    public static String receberRua() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite a rua: ");
            String rua = entrada.nextLine().trim();

            if (rua.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (rua.length() >= 2) {
                if (validarTexto(rua)) {
                    return rua;
                } else {
                    System.out.println("Erro: Digite uma rua válida com apenas letras e espaços.");
                }
            } else {
                System.out.println("Erro: A rua deve ter pelo menos 2 caracteres.");
            }
        }
    }

    public static String receberNumero() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o número: ");
            String numero = entrada.nextLine().trim();

            if (numero.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (numero.length() >= 1) {
                if (validarNumeroEndereco(numero)) {
                    return numero;
                } else {
                    System.out.println("Erro: Digite um número válido (pode conter números, letras, / ou -).");
                }
            } else {
                System.out.println("Erro: O número não pode estar vazio.");
            }
        }
    }

    public static boolean validarNumeroEndereco(String numero) {
        return numero.matches("[0-9A-Za-z\\/\\-\\s]+");
    }

    public static String receberBairro() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o bairro: ");
            String bairro = entrada.nextLine().trim();

            if (bairro.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (bairro.length() >= 2) {
                if (validarTexto(bairro)) {
                    return bairro;
                } else {
                    System.out.println("Erro: Digite um bairro válido com apenas letras e espaços.");
                }
            } else {
                System.out.println("Erro: O bairro deve ter pelo menos 2 caracteres.");
            }
        }
    }

    public static String receberCidade() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite a cidade: ");
            String cidade = entrada.nextLine().trim();

            if (cidade.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (cidade.length() >= 2) {
                if (validarTexto(cidade)) {
                    return cidade;
                } else {
                    System.out.println("Erro: Digite uma cidade válida com apenas letras e espaços.");
                }
            } else {
                System.out.println("Erro: A cidade deve ter pelo menos 2 caracteres.");
            }
        }
    }

    public static String receberEstado() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o estado (sigla, ex: SP, RJ): ");
            String estado = entrada.nextLine().trim().toUpperCase();

            if (estado.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (estado.length() == 2) {
                if (validarEstado(estado)) {
                    return estado;
                } else {
                    System.out.println("Erro: Digite uma sigla de estado válida.");
                }
            } else {
                System.out.println("Erro: O estado deve ser uma sigla de 2 letras.");
            }
        }
    }

    public static boolean validarEstado(String estado) {
        String[] estados = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO",
                "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR",
                "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};

        for (String sigla : estados) {
            if (sigla.equals(estado)) {
                return true;
            }
        }
        return false;
    }

    public static String receberCEP() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o CEP (apenas números): ");
            String cep = entrada.nextLine().trim();

            if (cep.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (cep.length() == 8) {
                if (validarCEP(cep)) {
                    return formatarCEP(cep);
                } else {
                    System.out.println("Erro: Digite um CEP válido com 8 dígitos.");
                }
            } else {
                System.out.println("Erro: O CEP deve ter exatamente 8 dígitos.");
            }
        }
    }

    public static boolean validarCEP(String cep) {
        return cep.matches("[0-9]{8}");
    }

    public static String formatarCEP(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    public static String receberComplemento() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o complemento: ");
            String complemento = entrada.nextLine().trim();

            if (complemento.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (complemento.isEmpty()) {
                System.out.println("Erro: O complemento é obrigatório! Digite uma informação válida.");
                continue;
            }

            if (complemento.length() <= 100) {
                return complemento;
            } else {
                System.out.println("Erro: O complemento deve ter no máximo 100 caracteres.");
            }
        }
    }


}
