package com.ead.authuser.constants;

public class DBConstants {
    public static final String DROP_CONSTRAINT_USUARIO = "begin; alter table usuarios_acesso drop CONSTRAINT ";
    public static final String UPDATE_ACESSO_END_POINT = "begin; update acesso_end_point set qtd_acesso_end_point = qtd_acesso_end_point + 1; commit;";
}
