package pl.ferdezo.dockerizing.notification;

import java.util.function.Consumer;

import pl.ferdezo.dockerizing.model.Identifiable;

@FunctionalInterface
public interface Notifier extends Consumer<Identifiable> {}
