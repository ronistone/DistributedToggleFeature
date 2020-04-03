package br.com.ronistone.feature.toggle;

import java.io.Serializable;

public interface Feature extends Serializable {


    String name();
    Object getValue();

}
