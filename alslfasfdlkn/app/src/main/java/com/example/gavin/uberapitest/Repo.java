package com.example.gavin.uberapitest;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Gavin on 5/09/2016.
 */
/*public class RepoContainer {
    public List<Repo> repos;

    public void addRepo(String id, String name, String owner) {
        Repo newRepo = new Repo(id, name, owner);
        repos.add(newRepo);
    }

    public Repo getRepo(int i) {
        return repos.get(i);
    }
*/
    public class Repo {
        private String id;
        private String name;
        private Owner owner;

    public Repo(String id, String name, Owner owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {

            return name;
        }

        public Owner getOwner() {
            return owner;
        }

        public String getId() {

            return id;
        }
    }
//}
