/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.brazil;

import com.makesystem.pidgey.formatation.StringFormat;


/**
 *
 * @author Richeli.vargas
 */
public enum Uf {

    AC("AC", "Acre", 69900000, 69999999),
    AL("AL", "Alagoas", 57000000, 57999999),
    AP("AP", "Amapá", 68900000, 68999999),
    AM("AM", "Amazonas", 69400000, 69899999),
    BA("BA", "Bahia", 40000000, 48999999),
    CE("CE", "Ceará", 60000000, 63999999),
    DF("DF", "Distrito Federal", 70000000, 73699999),
    ES("ES", "Espírito Santo", 29000000, 29999999),
    GO("GO", "Goiás", 72800000, 76799999),
    MA("MA", "Maranhão", 65000000, 65999999),
    MT("MT", "Mato Grosso", 78000000, 78899999),
    MS("MS", "Mato Grosso do Sul", 79000000, 79999999),
    MG("MG", "Minas Gerais", 30000000, 39999999),
    PA("PA", "Pará", 66000000, 68899999),
    PB("PB", "Paraíba", 58000000, 58999999),
    PR("PR", "Paraná", 80000000, 87999999),
    PE("PE", "Pernambuco", 50000000, 56999999),
    PI("PI", "Piauí", 64000000, 64999999),
    RJ("RJ", "Rio de Janeiro", 20000000, 28999999),
    RN("RN", "Rio Grande do Norte", 59000000, 59999999),
    RS("RS", "Rio Grande do Sul", 90000000, 99999999),
    RO("RO", "Rondônia", 78900000, 78999999),
    RR("RR", "Roraima", 69300000, 69389999),
    SC("SC", "Santa Catarina", 88000000, 89999999),
    SP("SP", "São Paulo", 1000000, 19999999),
    SE("SE", "Sergipe", 49000000, 49999999),
    TO("TO", "Tocantins", 77000000, 77995999);

    private final String uf;
    private final String nome;
    private final long minCep;
    private final long maxCep;

    private Uf(String uf, String nome, long minCep, long maxCep) {
        this.uf = uf;
        this.nome = nome;
        this.minCep = minCep;
        this.maxCep = maxCep;
    }

    public String getUf() {
        return uf;
    }

    public String getNome() {
        return nome;
    }

    public long getMinCep() {
        return minCep;
    }

    public long getMaxCep() {
        return maxCep;
    }

    public static Uf fromString(final String uf) {

        if (uf == null || uf.trim().isEmpty()) {
            return null;
        }

        for (Uf _uf : values()) {
            if (_uf.toString().equals(uf.toUpperCase())
                    || _uf.getUf().equals(uf)
                    || _uf.getNome().toUpperCase().equals(uf.toUpperCase())) {
                return _uf;
            }
        }
        return null;
    }

    public static Uf fromCep(final String cep) {

        if(cep == null){
            return null;
        }
        
        final String toNumeric = StringFormat.onlyNumbers(cep);
        
        if (toNumeric.isEmpty()) {
            return null;
        }

        final long numeric = Long.parseLong(toNumeric);
        
        for (Uf _uf : values()) {
            if (numeric >= _uf.getMinCep() && numeric <= _uf.getMaxCep()) {
                return _uf;
            }
        }
        return null;
    }
    
    public static Uf fromDDD(final String ddd) {
        final DDD _ddd = DDD.fromString(ddd);
        return _ddd == null ? null : _ddd.getUf();
    }
}
