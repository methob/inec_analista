package br.inec.com.inec_desafio_analista.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jonathan on 27/12/2017.
 */

public class User implements Serializable {

    private Integer id;

    private String login;

    private String email;

    @SerializedName("avatar_url")
    private String avatarURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
