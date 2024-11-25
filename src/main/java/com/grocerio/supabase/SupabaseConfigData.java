package com.grocerio.supabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupabaseConfigData {
    String db_host;
    String db_name;
    String db_port;
    String db_user;
    String db_password;
    String api_url;
    String api_key;
    String api_key_secret;
    String api_jwt_secret;
}
