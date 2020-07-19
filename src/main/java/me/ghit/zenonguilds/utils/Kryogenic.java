package me.ghit.zenonguilds.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.unsafe.UnsafeInput;
import com.esotericsoftware.kryo.unsafe.UnsafeOutput;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import static com.esotericsoftware.kryo.util.Util.unsafe;

public interface Kryogenic {
    Kryo KRYO = new Kryo() {{
        setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        setRegistrationRequired(false);
    }};

    static void freeze(Path file, Object value) {
        try {
            if (file.getParent() != null)
                Files.createDirectories(file.getParent());
            try (final Output out = unsafe ? new UnsafeOutput(Files.newOutputStream(file)) : new Output(Files.newOutputStream(file))) {
                KRYO.writeClassAndObject(out, value);
            }
        } catch (Throwable reason) { throw new RuntimeException(reason); }
    }

    static <Type> Type thaw(Path file, Supplier<Type> defaultValue) {
        if (!Files.exists(file)) return defaultValue.get();
        try (final Input in = unsafe ? new UnsafeInput(Files.newInputStream(file)) : new Input(Files.newInputStream(file))) {
            final Type value = (Type) KRYO.readClassAndObject(in);
            return value == null ? defaultValue.get() : value;
        } catch (Throwable reason) { throw new RuntimeException(reason); }
    }

    static <Type> Type thaw(Path file, Type defaultValue) { return thaw(file, () -> defaultValue); }
    static <Type> Type thaw(Path file) { return thaw(file, null); }
}
