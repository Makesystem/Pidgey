package com.makesystem.pidgey.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtil extends StringUtilImpl {

    private static String getValueFrom(final List<String> listChaves, final String string) {

        String valor = "";
        int tamanhoChave = 0;

        for (String chaveValue : listChaves) {

            int posicaoInicial = string.toUpperCase().lastIndexOf(chaveValue.toUpperCase());

            if (posicaoInicial > -1) {
                int chaveValueLenght = chaveValue.length();
                if (chaveValueLenght > tamanhoChave) {
                    String value = getValueFrom(posicaoInicial + chaveValueLenght, string);
                    if (!value.trim().isEmpty() && !listChaves.contains(value)) {
                        tamanhoChave = chaveValueLenght;
                        valor = value;
                    }
                }
            }
        }
        return valor;
    }

    private static String getValueFrom(int posicaoInicial, String string) {

        String value = string.substring(posicaoInicial, string.length()).trim();
        value = delAllDifferentOf(value, "[^a-zA-Z0-9 ]+").trim();

        if (value.contains(" ")) {
            return value.substring(0, value.indexOf(" ")).trim();
        }
        return value;
    }

    @SuppressWarnings("IndexOfReplaceableByContains")
    private static FilteredAddress filterAddresses(final FilteredAddress address) {

        try {

            String originalAddress = address.originalValueSite;
            originalAddress = cutValue(address.separatorsOriginalLogradouro, originalAddress);
            String searchedAddress = address.searchedValue;
            searchedAddress = cutValue(address.separatorsOriginalLogradouro, searchedAddress);

            originalAddress = originalAddress.replaceAll("[^a-zA-Z0-9 ]+", " ");
            originalAddress = justOnlyOneSpace(originalAddress).trim().toUpperCase();
            originalAddress = removeAccents(originalAddress);

            searchedAddress = searchedAddress.replaceAll("[^a-zA-Z0-9 ]+", " ");
            searchedAddress = justOnlyOneSpace(searchedAddress).trim().toUpperCase();
            searchedAddress = removeAccents(searchedAddress);

            final String[] searchedWords = searchedAddress.split(" ");
            final String[] originalWords = originalAddress.split(" ");
            final List<String> wordsFound = new LinkedList<>();

            address.qtdeWords = searchedWords.length;

            final Set<String> aux = new HashSet<>();

            for (final String searchedWord : searchedWords) {

                if (!aux.add(searchedWord)) {
                    continue;
                }

                final Set<String> aux2 = new HashSet<>();

                for (final String originalWord : originalWords) {

                    if (!aux2.add(originalWord)) {
                        continue;
                    }

                    if (originalWord.equals(searchedWord)) {

                        int position = originalAddress.indexOf(searchedWord);
                        if (position == -1) {
                            position = searchedAddress.indexOf(originalWord);
                        }

                        address.qtdequals++;
                        wordsFound.add(searchedWord);
                        address.qtdeWordsFound++;

                        if (address.inicialPositionWordFound == -1 || position < address.inicialPositionWordFound) {
                            address.inicialPositionWordFound = position;
                        }

                    } else {

                        int position = originalWord.indexOf(searchedWord);

                        if (position != -1) {

                            final int searchedWordLength = searchedWord.length();
                            final int originalWordLength = originalWord.length();

                            if (((searchedWordLength * 100) / originalWordLength) > 50) {

                                wordsFound.add(searchedWord);
                                address.qtdeWordsFound++;

                                if (address.inicialPositionWordFound == -1 || position < address.inicialPositionWordFound) {
                                    address.inicialPositionWordFound = position;
                                }
                            }

                        } else {

                            position = searchedWord.indexOf(originalWord);

                            if (position != -1) {

                                final int searchedWordLength = searchedWord.length();
                                final int originalWordLength = originalWord.length();

                                if (((originalWordLength * 100) / searchedWordLength) > 50) {

                                    wordsFound.add(originalWord);
                                    address.qtdeWordsFound++;

                                    if (address.inicialPositionWordFound == -1 || position < address.inicialPositionWordFound) {
                                        address.inicialPositionWordFound = position;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!wordsFound.isEmpty()) {
                address.wordsFound = wordsFound;
            }

            return address;

        } finally {
            address.totFilters++;
            if (address.qtdeWordsFound != 0) {
                address.qtdeAssertiveFilters++;
            }
        }
    }

    /**
     * Usada para controle
     *
     * @param <T>
     */
    public static class FilteredAddress<T> {

        public String[] separatorsOriginalLogradouro;
        public String[] separatorsSearchedLogradouro;

        /**
         * Total de filtros que passaram com sucesso pelo filtro, ex: Foi
         * utilizado logradouro e cep para filtrar, mas só foi localizado o cep.
         * Neste caso, terá o valor 1.
         */
        public int qtdeAssertiveFilters;
        /**
         * Total de filtros que foram aplicados, ex: Foi utilizado o logradouro
         * e cep para filtrar, neste caso, terá o valor 2.
         */
        public int totFilters;

        /**
         * Qunatidade de palavras com a mesma escrita
         */
        public int qtdequals;
        /**
         * A posição inicial que foi encontrada a palavra, caso ela tenha sido
         * encontrada. Ex: Foi utilizado a palavra 'sergio gomes' para filtrar
         * em cima da palvra 'rua sergio gomes'. Neste caso, terá o valor 4.
         */
        public int inicialPositionWordFound = -1;
        /**
         * Qtde de palavras encontradas, ex: Foi utilizado a palavra 'sergio
         * gomes' para filtrar, mas foi encontrado só 'gomes'. Neste caso, terá
         * o valor 1.
         */
        public int qtdeWordsFound;
        /**
         * Qtde palavras do logradouro utilizado para filtrar, ex: Foi utilizado
         * a palavra 'sergio gomes' para filtrar. Neste caso, terá o valor 2.
         */
        public int qtdeWords;
        /**
         * Logradouro escrito da mesma forma como esta no site.
         */
        public String originalValueSite;
        /**
         * Qualquer objeto que se deseje retornar juntamente com o endereço
         * filtrado.
         */
        public T t;
        /**
         * Logradouro encontrado.
         */
        public List<String> wordsFound;
        /**
         * Logradouro utilizado para a pesquisa.
         */
        public String searchedValue;
        /**
         * Número encontrado.
         */
        public String number;

        /**
         * Quadra encontrada.
         */
        public String square;

        /**
         * Lote encontrada.
         */
        public String lot;

        /**
         * Bloco encontrado.
         */
        public String block;

        /**
         * Apartamento encontrado.
         */
        public String apto;
    }

    private static List<FilteredAddress> filterAddresses(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            final FilteredAddress filteredAddress = filterAddresses(address);
            if (filteredAddress.wordsFound != null) {
                list.add(address);
            }
        });
        return list;
    }

    @SuppressWarnings("IndexOfReplaceableByContains")
    private static List<FilteredAddress> filterNumber(final List<FilteredAddress> addresses, final String number) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            if (number != null && !number.trim().isEmpty()) {
                final String numberFound = getNumberOrDefault(address.originalValueSite, "");
                if (!numberFound.isEmpty() && numberFound.equals(number)) {
                    address.qtdeAssertiveFilters++;
                    address.number = number;
                    list.add(address);
                }
            }
            address.totFilters++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterSquare(final List<FilteredAddress> addresses, final String square) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            if (square != null && !square.trim().isEmpty()) {
                final String blockFound = getSquareOrDefault(address.originalValueSite, "");
                if (!blockFound.isEmpty() && blockFound.equalsIgnoreCase(square)) {
                    address.qtdeAssertiveFilters++;
                    address.square = square;
                    list.add(address);
                }
            }
            address.totFilters++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterLot(final List<FilteredAddress> addresses, final String lot) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            if (lot != null && !lot.trim().isEmpty()) {
                final String lotFound = getLotOrDefault(address.originalValueSite, "");
                if (!lotFound.isEmpty() && lotFound.equalsIgnoreCase(lot)) {
                    address.qtdeAssertiveFilters++;
                    address.lot = lot;
                    list.add(address);
                }
            }
            address.totFilters++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterBlock(final List<FilteredAddress> addresses, final String block) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            if (block != null && !block.trim().isEmpty()) {
                final String blockFound = getBlockOrDefault(address.originalValueSite, "");
                if (!blockFound.isEmpty() && blockFound.equalsIgnoreCase(block)) {
                    address.qtdeAssertiveFilters++;
                    address.block = block;
                    list.add(address);
                }
            }
            address.totFilters++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterApto(final List<FilteredAddress> addresses, final String apto) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            if (apto != null && !apto.trim().isEmpty()) {
                final String aptoFound = getAptoOrDefault(address.originalValueSite, "");
                if (!aptoFound.isEmpty() && aptoFound.equalsIgnoreCase(apto)) {
                    address.qtdeAssertiveFilters++;
                    address.apto = apto;
                    list.add(address);
                }
            }
            address.totFilters++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterAssertiveness(List<FilteredAddress> addresses) {

        List<FilteredAddress> listEndPorAcertividade;

        //listEndPorAcertividade = filterAddressEqualsWords(listEndPorAcertividade);
        listEndPorAcertividade = filterAddressesLength(addresses);

        if (listEndPorAcertividade.size() <= 1) {
            return listEndPorAcertividade;
        }

        int ultimoValorAcertivo = -1;

        for (final FilteredAddress entity : addresses) {

            if (entity.qtdeAssertiveFilters > ultimoValorAcertivo) {

                listEndPorAcertividade.clear();
                listEndPorAcertividade.add(entity);
                ultimoValorAcertivo = entity.qtdeAssertiveFilters;

            } else if (entity.qtdeAssertiveFilters == ultimoValorAcertivo) {

                listEndPorAcertividade.add(entity);
            }
        }

        if (listEndPorAcertividade.size() == 1) {
            return listEndPorAcertividade;
        }

        listEndPorAcertividade = filterAddressInicialPosition(listEndPorAcertividade);

        // fazer uma ordenação pelo tamanho, o menor vem primeiro
        if (listEndPorAcertividade.size() > 1) {
            listEndPorAcertividade = listEndPorAcertividade.stream().sorted((a, b) -> {

                if (a.originalValueSite.length() < b.originalValueSite.length()) {
                    return -1;
                } else if (a.originalValueSite.length() == b.originalValueSite.length()) {
                    return 0;
                } else {
                    return 1;
                }

            }).collect(Collectors.toList());
        }

        return listEndPorAcertividade;
    }

    public static <T> List<FilteredAddress> filter(final List<String> list, String address, final String number, final String complement, T t,
            final String[] separatorOriginalLogradouro, final String[] separatorSearchedLogradouro) {

        final List<FilteredAddress> listFilteredAddress = new ArrayList<>();

        if (address == null || address.trim().isEmpty() || list == null || list.isEmpty()) {
            return listFilteredAddress;
        }

        final StringUtil stringUtils = new StringUtil();

        list.forEach((originalAddress) -> {
            FilteredAddress filteredAddress = new FilteredAddress<>();
            filteredAddress.originalValueSite = originalAddress;
            filteredAddress.separatorsOriginalLogradouro = separatorOriginalLogradouro;
            filteredAddress.separatorsSearchedLogradouro = separatorSearchedLogradouro;
            filteredAddress.searchedValue = address;
            filteredAddress.t = t;
            listFilteredAddress.add(filteredAddress);
        });

        return filterAddresses(listFilteredAddress, number, complement);
    }

    private static List<FilteredAddress> filterAddresses(List<FilteredAddress> filteredAddress, final String number, final String complement) {

        if (filteredAddress.isEmpty()) {
            return filteredAddress;
        }

        filteredAddress = filterAddresses(filteredAddress);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        /**
         * ANALISA O NUMERO
         */
        filteredAddress = filterNumber(filteredAddress, number);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        /**
         * ANALISA A QUADRA
         */
        final String squareTemp = getSquareOrDefault(filteredAddress.get(0).searchedValue, "");

        String square;
        if (squareTemp.isEmpty()) {
            square = getSquareOrDefault(complement, "");
        } else {
            square = squareTemp;
        }

        filteredAddress = filterSquare(filteredAddress, square);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        /**
         * ANALISA O LOTE
         */
        final String lotTemp = getLotOrDefault(filteredAddress.get(0).searchedValue, "");

        String lot;
        if (lotTemp.isEmpty()) {
            lot = getLotOrDefault(complement, "");
        } else {
            lot = lotTemp;
        }

        filteredAddress = filterLot(filteredAddress, lot);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        /**
         * ANALISA O BLOCO
         */
        final String blockTemp = getBlockOrDefault(filteredAddress.get(0).searchedValue, "");

        String block;
        if (blockTemp.isEmpty()) {
            block = getBlockOrDefault(complement, "");
        } else {
            block = blockTemp;
        }

        filteredAddress = filterBlock(filteredAddress, block);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        /**
         * ANALISA O APARATAMENTO
         */
        final String aptoTemp = getAptoOrDefault(filteredAddress.get(0).searchedValue, "");

        String apto;
        if (aptoTemp.isEmpty()) {
            apto = getAptoOrDefault(complement, "");
        } else {
            apto = aptoTemp;
        }

        filteredAddress = filterApto(filteredAddress, apto);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

        return filterAssertiveness(filteredAddress);
    }

    private static List<FilteredAddress> filterAddressEqualsWords(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> listTemp = new LinkedList<>();
        int lastEqualsWord = -1;

        for (final FilteredAddress entity : addresses) {

            if (entity.qtdequals <= 0) {
                continue;
            }

            if (entity.qtdequals > lastEqualsWord) {

                listTemp.clear();
                listTemp.add(entity);
                lastEqualsWord = entity.qtdequals;

            } else if (entity.qtdequals == lastEqualsWord) {

                listTemp.add(entity);
            }
        }

        return listTemp;
    }

    private static List<FilteredAddress> filterAddressInicialPosition(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> listTemp = new LinkedList<>();
        int inicialPositionLasWordFound = 90000000; // qualquer numero muito grande

        for (final FilteredAddress entity : addresses) {

            if (entity.inicialPositionWordFound == -1) {
                continue;
            }

            if (entity.inicialPositionWordFound < inicialPositionLasWordFound) {

                listTemp.clear();
                listTemp.add(entity);
                inicialPositionLasWordFound = entity.inicialPositionWordFound;

            } else if (entity.inicialPositionWordFound == inicialPositionLasWordFound) {

                listTemp.add(entity);
            }
        }

        return listTemp;
    }

    private static String cutValue(final String[] separators, final String originalValue) {
        if (separators != null && separators.length > 0) {
            String value = originalValue;
            for (final String separator : separators) {
                final int index = value.indexOf(separator);
                if (index > 0) {
                    value = value.substring(0, index);
                }
            }
            return value;
        }
        return originalValue;
    }

    private static List<FilteredAddress> filterAddressesLength(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> listTemp = new LinkedList<>();
        int lenthLastWordFound = 0;

        for (final FilteredAddress address : addresses) {

            if (address.wordsFound == null || address.wordsFound.isEmpty()) {
                continue;
            }

            int lengthWordsFound = 0;

            // nao remover este codigo do java 7
            for (final Object value : address.wordsFound) {
                lengthWordsFound += ((String) value).length();
            }

            if (lengthWordsFound > 0) {

                boolean continueAux = false;

                if (lengthWordsFound > 4) {
                    continueAux = true;
                } else {

                    String originalAddress = address.originalValueSite;
                    originalAddress = cutValue(address.separatorsOriginalLogradouro, originalAddress);
                    String searchedAddress = address.searchedValue;
                    searchedAddress = cutValue(address.separatorsOriginalLogradouro, searchedAddress);

                    final int lengthOriginalAdress = delAllDifferentOf(originalAddress, "[^a-zA-Z0-9]").length();
                    final int lenthSearchedAddress = delAllDifferentOf(searchedAddress, "[^a-zA-Z0-9]").length();

                    int acceptedPercentage;

                    if (lengthOriginalAdress < 12) {
                        acceptedPercentage = 32;
                    } else if (lengthOriginalAdress < 18) {
                        acceptedPercentage = 22;
                    } else if (lengthOriginalAdress < 25) {
                        acceptedPercentage = 15;
                    } else if (lengthOriginalAdress < 40) {
                        acceptedPercentage = 12;
                    } else {
                        acceptedPercentage = 9;
                    }

                    if (lengthOriginalAdress > lenthSearchedAddress) {
                        if (((lengthWordsFound * 100) / lengthOriginalAdress) >= acceptedPercentage) {
                            continueAux = true;
                        }
                    } else {
                        if (((lengthWordsFound * 100) / lenthSearchedAddress) >= acceptedPercentage) {
                            continueAux = true;
                        }
                    }
                }

                if (continueAux) {

                    if (lengthWordsFound > lenthLastWordFound) {

                        listTemp.clear();
                        listTemp.add(address);
                        lenthLastWordFound = lengthWordsFound;

                    } else if (lengthWordsFound == lenthLastWordFound) {

                        listTemp.add(address);
                    }
                }
            }
        }

        return listTemp;
    }

    public static String getLotOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getLot(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getSquareOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getSquare(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getBlockOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getBlock(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getNumberOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getNumber(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getCepOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getCep(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getAptoOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getApto(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getComplementOrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getComplement(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getComplement1OrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getComplement1(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return returnValue;
        }
        return returnValue;
    }

    public static String getComplement2OrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getComplement2(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getComplement3OrDefault(final String string, String defaultValue) {

        if (string == null || string.trim().isEmpty()) {
            return defaultValue;
        }
        final String returnValue = getComplement3(string);

        if (returnValue == null || returnValue.trim().isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    public static String getApto(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("APARTAMENTO");
        list.add("APARTAMENT");
        list.add("APARTAMEN");
        list.add("APARTAME");
        list.add("APARTAM");
        list.add("APTOO");
        list.add("APTO");
        list.add("APTO.");
        list.add("APT");
        list.add("APT.");

        return getValueFrom(list, string);
    }

    public static String getComplement3(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("COMPLEMENTO 3");
        list.add("COMPLEMENTO3");
        list.add("COMPLEMENT 3");
        list.add("COMPLEMENT3");
        list.add("COMPLEMEN 3");
        list.add("COMPLEMEN3");
        list.add("COMPLEME 3");
        list.add("COMPLEME3");
        list.add("COMPLEM 3");
        list.add("COMPLEM3");
        list.add("COMPLE3");
        list.add("COMPL3");
        list.add("COMPL3");

        return getValueFrom(list, string);
    }

    public static String getComplement2(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("COMPLEMENTO 2");
        list.add("COMPLEMENTO2");
        list.add("COMPLEMENT 2");
        list.add("COMPLEMENT2");
        list.add("COMPLEMEN 2");
        list.add("COMPLEMEN2");
        list.add("COMPLEME 2");
        list.add("COMPLEME2");
        list.add("COMPLEM 2");
        list.add("COMPLEM2");
        list.add("COMPLE2");
        list.add("COMPL2");
        list.add("COMP2");

        return getValueFrom(list, string);
    }

    public static String getComplement1(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("COMPLEMENTO 1");
        list.add("COMPLEMENTO1");
        list.add("COMPLEMENT 1");
        list.add("COMPLEMENT1");
        list.add("COMPLEMEN 1");
        list.add("COMPLEMEN1");
        list.add("COMPLEME 1");
        list.add("COMPLEME1");
        list.add("COMPLEM 1");
        list.add("COMPLEM1");
        list.add("COMPLE1");
        list.add("COMPL1");
        list.add("COMP1");

        return getValueFrom(list, string);
    }

    public static String getComplement(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("COMPL");
        list.add("COMPLEMENTO");
        list.add("COMPLEM");
        list.add("COMP");
        list.add("COMPLEMENT");
        list.add("COMPLEMEN");
        list.add("COMPLEME");
        list.add("COMPLE");

        return getValueFrom(list, string);
    }

    public static String getCep(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("CEP");
        list.add("CEPP");
        list.add("CEEP");
        list.add("CCEP");
        list.add("CODIGO DE ENDERECAMENTO POSTAL");

        return getValueFrom(list, string);
    }

    public static String getNumber(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("Nº");
        list.add("NUM");
        list.add("NUMERO");
        list.add("NUME");
        list.add("NUMER");
        list.add("NUMBER");

        return getValueFrom(list, string);
    }

    public static String getSquare(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("QD");
        list.add("QUADRA");
        list.add("QDR");
        list.add("QUADRAS");

        return getValueFrom(list, string);
    }

    public static String getLot(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("LT");
        list.add("LOTE");
        list.add("LOT");
        list.add("LOTES");

        return getValueFrom(list, string);
    }

    public static String getBlock(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("BL");
        list.add("BLOCO");
        list.add("BLC");
        list.add("BLOCOS");

        return getValueFrom(list, string);
    }

    public static class EndDataUtils {

        public String number;
        public String square;
        public String lot;
        public String block;
        public String apto;
    }

    public static EndDataUtils getEndDataUtils(final String string) {

        if (string == null || string.isEmpty()) {
            return null;
        }

        final EndDataUtils entity = new EndDataUtils();

        entity.number = getNumber(string);
        entity.square = getSquare(string);
        entity.lot = getLot(string);
        entity.block = getBlock(string);
        entity.apto = getApto(string);

        return entity;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("APARECIDA DE GOIANIA");
        list.add("GOIANIA");

        List<FilteredAddress> address = filter(list, "GOIANIA", null, null, null, null, null);

        if (address.isEmpty()) {
            System.out.println("nenhum resultado encontrado.");
        } else {
            for (FilteredAddress addres : address) {
                System.out.println("---------------------------------------------------");
                System.out.println("searchedAddress: " + addres.searchedValue);
                System.out.println("originalAddressSite: " + addres.originalValueSite);
                System.out.println("words found:");
                for (Object value : addres.wordsFound) {
                    System.out.println(value);
                }
                System.out.println("number: " + addres.number);
                System.out.println("square: " + addres.square);
                System.out.println("lot: " + addres.lot);
                System.out.println("block: " + addres.block);
                System.out.println("inicialPositionWordFound: " + addres.inicialPositionWordFound);
                System.out.println("qtdeAssertiveFilters: " + addres.qtdeAssertiveFilters);
                System.out.println("qtdeWords: " + addres.qtdeWords);
                System.out.println("qtdeWordsFound: " + addres.qtdeWordsFound);
                System.out.println("totFilters: " + addres.totFilters);
            }
        }
    }

}
