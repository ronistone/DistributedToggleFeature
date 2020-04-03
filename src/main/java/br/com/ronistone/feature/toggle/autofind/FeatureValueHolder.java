package br.com.ronistone.feature.toggle.autofind;

import java.util.UUID;

public class FeatureValueHolder {

    UUID idLastChange;
    String name;
    Object value;
    FeatureChangeNotifyType type;

    private FeatureValueHolder() {}

    public FeatureValueHolder(UUID idLastChange, String name, Object value, FeatureChangeNotifyType type) {
        this.idLastChange = idLastChange;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public UUID getIdLastChange() {
        return idLastChange;
    }

    public void setIdLastChange(UUID idLastChange) {
        this.idLastChange = idLastChange;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public FeatureChangeNotifyType getType() {
        return type;
    }

    public void setType(FeatureChangeNotifyType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static FeatureValueHolderBuilder build() {
        return new FeatureValueHolderBuilder(new FeatureValueHolder());
    }

    public static class FeatureValueHolderBuilder {

        private FeatureValueHolder holder;

        private FeatureValueHolderBuilder(FeatureValueHolder holder) {
            this.holder = holder;
        }

        public FeatureValueHolderBuilder name(String name) {
            holder.setName(name);
            return this;
        }

        public FeatureValueHolderBuilder value(Object value) {
            holder.setValue(value);
            return this;
        }

        public FeatureValueHolderBuilder idLastValue(UUID id) {
            holder.setIdLastChange(id);
            return this;
        }

        public FeatureValueHolderBuilder type(FeatureChangeNotifyType type) {
            holder.setType(type);
            return this;
        }

        public FeatureValueHolder create(){
            return holder;
        }
    }
}
