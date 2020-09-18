package com.makesystem.pidgey.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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

            address.qtdWords = searchedWords.length;

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

                        address.qtdEqualsWordsFound++;
                        wordsFound.add(searchedWord);
                        address.qtdWordsFound++;

                        if (address.initialPositionWordsFound == -1 || position < address.initialPositionWordsFound) {
                            address.initialPositionWordsFound = position;
                        }

                    } else {

                        int position = originalWord.indexOf(searchedWord);

                        if (position != -1) {

                            final int searchedWordLength = searchedWord.length();
                            final int originalWordLength = originalWord.length();

                            if (((searchedWordLength * 100) / originalWordLength) > 50) {

                                wordsFound.add(searchedWord);
                                address.qtdWordsFound++;

                                if (address.initialPositionWordsFound == -1 || position < address.initialPositionWordsFound) {
                                    address.initialPositionWordsFound = position;
                                }
                            }

                        } else {

                            position = searchedWord.indexOf(originalWord);

                            if (position != -1) {

                                final int searchedWordLength = searchedWord.length();
                                final int originalWordLength = originalWord.length();

                                if (((originalWordLength * 100) / searchedWordLength) > 50) {

                                    wordsFound.add(originalWord);
                                    address.qtdWordsFound++;

                                    if (address.initialPositionWordsFound == -1 || position < address.initialPositionWordsFound) {
                                        address.initialPositionWordsFound = position;
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
            address.entireFiltersApplied++;
            if (address.qtdWordsFound != 0) {
                address.qtdeAssertiveFilters++;
            }
        }
    }

    /**
     * Usada para controle
     *
     * @param <T>
     */
    public class FilteredAddress<T> {

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
        public int entireFiltersApplied;

        /**
         * Qunatidade de palavras com a mesma escrita
         */
        public int qtdEqualsWordsFound;
        /**
         * A posição inicial que foi encontrada a palavra, caso ela tenha sido
         * encontrada. Ex: Foi utilizado a palavra 'sergio gomes' para filtrar
         * em cima da palvra 'rua sergio gomes'. Neste caso, terá o valor 4.
         */
        public int initialPositionWordsFound = -1;
        /**
         * Qtde de palavras encontradas, ex: Foi utilizado a palavra 'sergio
         * gomes' para filtrar, mas foi encontrado só 'gomes'. Neste caso, terá
         * o valor 1.
         */
        public int qtdWordsFound;
        /**
         * Qtde palavras do logradouro utilizado para filtrar, ex: Foi utilizado
         * a palavra 'sergio gomes' para filtrar. Neste caso, terá o valor 2.
         */
        public int qtdWords;
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
    }

    private static List<FilteredAddress> filterAddresses(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> list = new ArrayList<>();

        addresses.forEach((address) -> {
            final FilteredAddress filteredAddress = StringUtil.filterAddresses(address);
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
            address.entireFiltersApplied++;
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
                final String blockFound = StringUtil.getSquareOrDefault(address.originalValueSite, "");
                if (!blockFound.isEmpty() && blockFound.equalsIgnoreCase(square)) {
                    address.qtdeAssertiveFilters++;
                    address.square = square;
                    list.add(address);
                }
            }
            address.entireFiltersApplied++;
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
                final String lotFound = StringUtil.getLotOrDefault(address.originalValueSite, "");
                if (!lotFound.isEmpty() && lotFound.equalsIgnoreCase(lot)) {
                    address.qtdeAssertiveFilters++;
                    address.lot = lot;
                    list.add(address);
                }
            }
            address.entireFiltersApplied++;
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
                final String blockFound = StringUtil.getBlockOrDefault(address.originalValueSite, "");
                if (!blockFound.isEmpty() && blockFound.equalsIgnoreCase(block)) {
                    address.qtdeAssertiveFilters++;
                    address.block = block;
                    list.add(address);
                }
            }
            address.entireFiltersApplied++;
        });

        if (list.isEmpty()) {
            return addresses;
        }

        return list;
    }

    private static List<FilteredAddress> filterAssertiveness(List<FilteredAddress> addresses) {

        List<FilteredAddress> listEndPorAcertividade;

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

        // fazer uma ordenação, primeiro pela assertividade, depois pelo tamanho, sendo que o menor vem primeiro.
        if (listEndPorAcertividade.size() > 1) {

            listEndPorAcertividade = listEndPorAcertividade.stream().sorted((a, b) -> {

                int aSum = a.qtdeAssertiveFilters + a.qtdWordsFound + a.qtdEqualsWordsFound;
                int bSum = b.qtdeAssertiveFilters + b.qtdWordsFound + b.qtdEqualsWordsFound;

                if (aSum > bSum) {
                    return -1;
                } else if (aSum < bSum) {
                    return 1;
                } else if (a.originalValueSite.length() < b.originalValueSite.length()) {
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
            FilteredAddress filteredAddress = stringUtils.new FilteredAddress<>();
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

        filteredAddress = filterNumber(filteredAddress, number);

        if (filteredAddress.isEmpty() || filteredAddress.size() == 1) {
            return filterAssertiveness(filteredAddress);
        }

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

        return filterAssertiveness(filteredAddress);
    }

    private static List<FilteredAddress> filterAddressEqualsWords(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> listTemp = new LinkedList<>();
        int lastEqualsWord = -1;

        for (final FilteredAddress entity : addresses) {

            if (entity.qtdEqualsWordsFound <= 0) {
                continue;
            }

            if (entity.qtdEqualsWordsFound > lastEqualsWord) {

                listTemp.clear();
                listTemp.add(entity);
                lastEqualsWord = entity.qtdEqualsWordsFound;

            } else if (entity.qtdEqualsWordsFound == lastEqualsWord) {

                listTemp.add(entity);
            }
        }

        return listTemp;
    }

    private static List<FilteredAddress> filterAddressInicialPosition(final List<FilteredAddress> addresses) {

        final List<FilteredAddress> listTemp = new LinkedList<>();
        int inicialPositionLasWordFound = 90000000; // qualquer numero muito grande

        for (final FilteredAddress entity : addresses) {

            if (entity.initialPositionWordsFound == -1) {
                continue;
            }

            if (entity.initialPositionWordsFound < inicialPositionLasWordFound) {

                listTemp.clear();
                listTemp.add(entity);
                inicialPositionLasWordFound = entity.initialPositionWordsFound;

            } else if (entity.initialPositionWordsFound == inicialPositionLasWordFound) {

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
        list.add("APT");

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

        list.add("COMPLEMENTO");
        list.add("COMPLEMENT");
        list.add("COMPLEMEN");
        list.add("COMPLEME");
        list.add("COMPLEM");
        list.add("COMPLE");
        list.add("COMPL");
        list.add("COMP");

        return getValueFrom(list, string);
    }

    public static String getCep(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new LinkedList<>();

        list.add("CODIGO DE ENDERECAMENTO POSTAL");
        list.add("CEPP");
        list.add("CEEP");
        list.add("CCEP");
        list.add("CEP");

        return getValueFrom(list, string);
    }

    public static String getNumber(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("Nº");
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

        list.add("QUADRAS");
        list.add("QUADRA");
        list.add("QDR");
        list.add("QD");

        return getValueFrom(list, string);
    }

    public static String getLot(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("LOTES");
        list.add("LOTE");
        list.add("LOT");
        list.add("LT");

        return getValueFrom(list, string);
    }

    public static String getBlock(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        final List<String> list = new ArrayList<>();

        list.add("BLOCOS");
        list.add("BLOCO");
        list.add("BLC");
        list.add("BL");

        return getValueFrom(list, string);
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("ADAMANTINA");
        list.add("AGUDOS");
        list.add("ALEGRETE");
        list.add("ALMIRANTE TAMANDARE");
        list.add("ALVARES MACHADO");
        list.add("ALVORADA");
        list.add("AMERICANA");
        list.add("AMPARO");
        list.add("ANANINDEUA");
        list.add("ANAPOLIS");
        list.add("ANDRADINA");
        list.add("APARECIDA");
        list.add("APARECIDA DE GOIANIA");
        list.add("APUCARANA");
        list.add("AQUIRAZ");
        list.add("ARACAJU");
        list.add("ARACATUBA");
        list.add("ARACOIABA DA SERRA");
        list.add("ARAGUAINA");
        list.add("ARAGUARI");
        list.add("ARAPONGAS");
        list.add("ARARAQUARA");
        list.add("ARARAS");
        list.add("ARAUCARIA");
        list.add("ARMACAO DOS BUZIOS");
        list.add("ARRAIAL DO CABO");
        list.add("ARTUR NOGUEIRA");
        list.add("ARUJA");
        list.add("ASSIS");
        list.add("ATIBAIA");
        list.add("BAGE");
        list.add("BALNEARIO CAMBORIU");
        list.add("BARRA BONITA");
        list.add("BARRA MANSA");
        list.add("BARRETOS");
        list.add("BARRINHA");
        list.add("BARUERI");
        list.add("BATATAIS");
        list.add("BAURU");
        list.add("BELEM");
        list.add("BELFORD ROXO");
        list.add("BELO HORIZONTE");
        list.add("BENTO GONCALVES");
        list.add("BERTIOGA");
        list.add("BETIM");
        list.add("BIGUACU");
        list.add("BIRIGUI");
        list.add("BLUMENAU");
        list.add("BLUMENAU BTV");
        list.add("BOA VISTA");
        list.add("BOITUVA");
        list.add("BOTUCATU");
        list.add("BRAGANCA PAULISTA");
        list.add("BRASILIA");
        list.add("BRUSQUE");
        list.add("CABEDELO");
        list.add("CABO FRIO");
        list.add("CACADOR");
        list.add("CACAPAVA");
        list.add("CACHOEIRA DO SUL");
        list.add("CACHOEIRA PAULISTA");
        list.add("CACHOEIRINHA");
        list.add("CACHOEIRO DE ITAPEMIRIM");
        list.add("CAIEIRAS");
        list.add("CALDAS NOVAS");
        list.add("CAMACARI");
        list.add("CAMBE");
        list.add("CAMBORIU");
        list.add("CAMPINA GRANDE");
        list.add("CAMPINAS");
        list.add("CAMPO BOM");
        list.add("CAMPO GRANDE");
        list.add("CAMPO LARGO");
        list.add("CAMPO LIMPO PAULISTA");
        list.add("CAMPOS DOS GOYTACAZES");
        list.add("CANELA");
        list.add("CANOAS");
        list.add("CAPAO DA CANOA");
        list.add("CAPIVARI");
        list.add("CARAGUATATUBA");
        list.add("CARAPICUIBA");
        list.add("CARAZINHO");
        list.add("CARIACICA");
        list.add("CARLOS BARBOSA");
        list.add("CARUARU");
        list.add("CASCAVEL");
        list.add("CASTANHAL");
        list.add("CATANDUVA");
        list.add("CAUCAIA");
        list.add("CAXIAS DO SUL");
        list.add("CERQUILHO");
        list.add("CHAPECO");
        list.add("CIANORTE");
        list.add("COLATINA");
        list.add("COLOMBO");
        list.add("CONSELHEIRO LAFAIETE");
        list.add("CONTAGEM");
        list.add("CORONEL FABRICIANO");
        list.add("CORUMBA");
        list.add("COSMOPOLIS");
        list.add("COTIA");
        list.add("CRAVINHOS");
        list.add("CRICIUMA");
        list.add("CRUZ ALTA");
        list.add("CRUZEIRO");
        list.add("CUBATAO");
        list.add("CUIABA");
        list.add("CURITIBA - CABO");
        list.add("CURITIBA - MMDS");
        list.add("DESCALVADO");
        list.add("DIADEMA");
        list.add("DIVINOPOLIS");
        list.add("DOURADOS");
        list.add("DRACENA");
        list.add("DUQUE DE CAXIAS");
        list.add("ELDORADO DO SUL");
        list.add("ELIAS FAUSTO");
        list.add("EMBU DAS ARTES");
        list.add("EMBU-GUACU");
        list.add("ERECHIM");
        list.add("ESPIRITO SANTO DO PINHAL");
        list.add("ESTANCIA VELHA");
        list.add("ESTEIO");
        list.add("EUSEBIO");
        list.add("FARROUPILHA");
        list.add("FEIRA DE SANTANA");
        list.add("FERNANDOPOLIS");
        list.add("FLORIANOPOLIS");
        list.add("FORTALEZA");
        list.add("FOZ DO IGUACU");
        list.add("FRAIBURGO");
        list.add("FRANCA");
        list.add("GARANHUNS");
        list.add("GASPAR");
        list.add("GOIANIA");
        list.add("GOVERNADOR VALADARES");
        list.add("GRAMADO");
        list.add("GRAVATAI");
        list.add("GUAIBA");
        list.add("GUARAMIRIM");
        list.add("GUARAPUAVA");
        list.add("GUARATINGUETA");
        list.add("GUARUJA");
        list.add("GUARULHOS");
        list.add("HORTOLANDIA");
        list.add("IBIRITE");
        list.add("IBIUNA");
        list.add("IGARACU DO TIETE");
        list.add("IGREJINHA");
        list.add("IJUI");
        list.add("ILHEUS");
        list.add("IMPERATRIZ");
        list.add("INDAIAL");
        list.add("INDAIATUBA");
        list.add("IPATINGA");
        list.add("IPERO");
        list.add("ITABIRA");
        list.add("ITABUNA");
        list.add("ITAJAI");
        list.add("ITAJUBA");
        list.add("ITANHAEM");
        list.add("ITAPECERICA DA SERRA");
        list.add("ITAPEMA");
        list.add("ITAPETININGA");
        list.add("ITAPEVA");
        list.add("ITAPEVI");
        list.add("ITAPIRA");
        list.add("ITAQUAQUECETUBA");
        list.add("ITATIBA");
        list.add("ITU");
        list.add("ITUIUTABA");
        list.add("ITUPEVA");
        list.add("ITUVERAVA");
        list.add("JABOATAO DOS GUARARAPES");
        list.add("JACAREI");
        list.add("JAGUARIUNA");
        list.add("JALES");
        list.add("JANDIRA");
        list.add("JARAGUA DO SUL");
        list.add("JAU");
        list.add("JOAO PESSOA");
        list.add("JOINVILLE");
        list.add("JUIZ DE FORA");
        list.add("JUNDIAI");
        list.add("LAGES");
        list.add("LAGOA SANTA");
        list.add("LAJEADO");
        list.add("LAURO DE FREITAS");
        list.add("LIMEIRA");
        list.add("LINS");
        list.add("LONDRINA");
        list.add("LORENA");
        list.add("LOUVEIRA");
        list.add("LUCAS DO RIO VERDE");
        list.add("MACAE");
        list.add("MACAPA");
        list.add("MACEIO");
        list.add("MAFRA");
        list.add("MAIRINQUE");
        list.add("MANAUS");
        list.add("MARABA");
        list.add("MARILIA");
        list.add("MARINGA");
        list.add("MATAO");
        list.add("MAUA");
        list.add("MESQUITA");
        list.add("MIRASSOL");
        list.add("MOCOCA");
        list.add("MOGI DAS CRUZES");
        list.add("MOGI GUACU");
        list.add("MOGI MIRIM");
        list.add("MONGAGUA");
        list.add("MONTE MOR");
        list.add("MONTENEGRO");
        list.add("MONTES CLAROS");
        list.add("MOSSORO");
        list.add("NATAL");
        list.add("NAVEGANTES");
        list.add("NILOPOLIS");
        list.add("NITEROI");
        list.add("NOVA FRIBURGO");
        list.add("NOVA IGUACU");
        list.add("NOVA LIMA");
        list.add("NOVA ODESSA");
        list.add("NOVO HAMBURGO - CABO");
        list.add("NOVO HAMBURGO - MMDS");
        list.add("OLINDA");
        list.add("ORLANDIA");
        list.add("OSASCO");
        list.add("OSORIO");
        list.add("PALHOCA");
        list.add("PALMAS");
        list.add("PARANAGUA");
        list.add("PARNAMIRIM");
        list.add("PAROBE");
        list.add("PASSO FUNDO");
        list.add("PAULINIA");
        list.add("PAULISTA");
        list.add("PEDREIRA");
        list.add("PELOTAS");
        list.add("PENAPOLIS");
        list.add("PERUIBE");
        list.add("PETROLINA");
        list.add("PETROPOLIS");
        list.add("PINDAMONHANGABA");
        list.add("PINHAIS");
        list.add("PIRACICABA");
        list.add("PIRASSUNUNGA");
        list.add("POA");
        list.add("POCOS DE CALDAS");
        list.add("PONTA GROSSA");
        list.add("PORTO ALEGRE - CABO");
        list.add("PORTO ALEGRE - MMDS");
        list.add("PORTO FELIZ");
        list.add("PORTO FERREIRA");
        list.add("PORTO VELHO");
        list.add("POTIM");
        list.add("POUSO ALEGRE");
        list.add("PRAIA GRANDE");
        list.add("PRESIDENTE BERNARDES");
        list.add("PRESIDENTE PRUDENTE");
        list.add("RAFARD");
        list.add("RECIFE");
        list.add("RESENDE");
        list.add("RIBEIRAO PIRES");
        list.add("RIBEIRAO PRETO");
        list.add("RIO BRANCO");
        list.add("RIO CLARO");
        list.add("RIO DAS OSTRAS");
        list.add("RIO DE JANEIRO");
        list.add("RIO GRANDE");
        list.add("RIO GRANDE DA SERRA");
        list.add("RIO NEGRINHO");
        list.add("RIO VERDE");
        list.add("ROLANDIA");
        list.add("RONDONOPOLIS");
        list.add("SABARA");
        list.add("SALTO");
        list.add("SALTO DE PIRAPORA");
        list.add("SALVADOR");
        list.add("SANTA BARBARA D OESTE");
        list.add("SANTA BRANCA");
        list.add("SANTA CRUZ DO RIO PARDO");
        list.add("SANTA CRUZ DO SUL");
        list.add("SANTA LUZIA");
        list.add("SANTA MARIA");
        list.add("SANTA ROSA");
        list.add("SANTANA DE PARNAIBA");
        list.add("SANTANA DO LIVRAMENTO");
        list.add("SANTO ANDRE");
        list.add("SANTO ANGELO");
        list.add("SANTOS");
        list.add("SANTOS ABC");
        list.add("SAO BENTO DO SUL");
        list.add("SAO BERNARDO DO CAMPO");
        list.add("SAO CAETANO DO SUL");
        list.add("SAO CARLOS");
        list.add("SAO GABRIEL");
        list.add("SAO GONCALO");
        list.add("SAO JOAO DE MERITI");
        list.add("SAO JOAQUIM DA BARRA");
        list.add("SAO JOSE");
        list.add("SAO JOSE DO RIO PARDO");
        list.add("SAO JOSE DO RIO PRETO");
        list.add("SAO JOSE DOS CAMPOS");
        list.add("SAO JOSE DOS PINHAIS");
        list.add("SAO LEOPOLDO - CABO");
        list.add("SAO LEOPOLDO - MMDS");
        list.add("SAO LUIS");
        list.add("SAO MIGUEL ARCANJO");
        list.add("SAO PAULO");
        list.add("SAO PEDRO DA ALDEIA");
        list.add("SAO ROQUE");
        list.add("SAO SEBASTIAO");
        list.add("SAO VICENTE");
        list.add("SAPIRANGA");
        list.add("SAPUCAIA DO SUL");
        list.add("SERRA");
        list.add("SERRA NEGRA");
        list.add("SERRANA");
        list.add("SERTAOZINHO");
        list.add("SETE LAGOAS");
        list.add("SINOP");
        list.add("SOBRAL");
        list.add("SOROCABA");
        list.add("SORRISO");
        list.add("SUMARE");
        list.add("SUZANO");
        list.add("TABOAO DA SERRA");
        list.add("TAQUARA");
        list.add("TATUI");
        list.add("TAUBATE");
        list.add("TEOFILO OTONI");
        list.add("TERESINA");
        list.add("TERESOPOLIS");
        list.add("TIETE");
        list.add("TIMOTEO");
        list.add("TOLEDO");
        list.add("TORRES");
        list.add("TREMEMBE");
        list.add("TRES CORACOES");
        list.add("TRES LAGOAS");
        list.add("TUPA");
        list.add("UBA");
        list.add("UBATUBA");
        list.add("UBERABA");
        list.add("UBERLANDIA");
        list.add("URUGUAIANA");
        list.add("VACARIA");
        list.add("VALINHOS");
        list.add("VALPARAISO DE GOIAS");
        list.add("VARGEM GRANDE PAULISTA");
        list.add("VARGINHA");
        list.add("VARZEA GRANDE");
        list.add("VARZEA PAULISTA");
        list.add("VENANCIO AIRES");
        list.add("VERA CRUZ");
        list.add("VESPASIANO");
        list.add("VIAMAO");
        list.add("VIDEIRA");
        list.add("VILA VELHA");
        list.add("VINHEDO");
        list.add("VITORIA");
        list.add("VITORIA DA CONQUISTA");
        list.add("VOLTA REDONDA");
        list.add("VOTORANTIM");
        list.add("XANGRI-LA");
        list.add("XANXERE");

        List<FilteredAddress> address = filter(list, "SAO LEOPOLDO", null, null, null, null, null);

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
                System.out.println("inicialPositionWordFound: " + addres.initialPositionWordsFound);
                System.out.println("qtdeAssertiveFilters: " + addres.qtdeAssertiveFilters);
                System.out.println("qtdeWords: " + addres.qtdWords);
                System.out.println("qtdeWordsFound: " + addres.qtdWordsFound);
                System.out.println("totFilters: " + addres.entireFiltersApplied);
            }
        }
    }

}
