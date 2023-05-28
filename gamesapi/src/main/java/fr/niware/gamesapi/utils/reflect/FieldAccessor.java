package fr.niware.gamesapi.utils.reflect;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldAccessor {
    private final Class<?> clazz;
    private final Unsafe unsafe;
    private final boolean isStatic;
    private final long fieldOffset;

    public FieldAccessor(Class<?> clazz, Field field, Unsafe unsafe) {
        this.clazz = clazz;
        this.unsafe = unsafe;
        this.isStatic = Modifier.isStatic(field.getModifiers());
        this.fieldOffset = this.isStatic ? unsafe.staticFieldOffset(field) : unsafe.objectFieldOffset(field);
    }

    public void setObject(Object instance, Object value) {
        if (this.isStatic) {
            this.unsafe.putObject(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putObject(instance, this.fieldOffset, value);
        }
    }

    public void setByte(Object instance, byte value) {
        if (this.isStatic) {
            this.unsafe.putByte(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putByte(instance, this.fieldOffset, value);
        }
    }

    public void setShort(Object instance, short value) {
        if (this.isStatic) {
            this.unsafe.putShort(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putShort(instance, this.fieldOffset, value);
        }
    }

    public void setInt(Object instance, int value) {
        if (this.isStatic) {
            this.unsafe.putInt(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putInt(instance, this.fieldOffset, value);
        }
    }

    public void setLong(Object instance, long value) {
        if (this.isStatic) {
            this.unsafe.putLong(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putLong(instance, this.fieldOffset, value);
        }
    }

    public void setFloat(Object instance, float value) {
        if (this.isStatic) {
            this.unsafe.putFloat(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putFloat(instance, this.fieldOffset, value);
        }
    }

    public void setDouble(Object instance, double value) {
        if (this.isStatic) {
            this.unsafe.putDouble(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putDouble(instance, this.fieldOffset, value);
        }
    }

    public void setChar(Object instance, char value) {
        if (this.isStatic) {
            this.unsafe.putChar(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putChar(instance, this.fieldOffset, value);
        }
    }

    public void setBoolean(Object instance, boolean value) {
        if (this.isStatic) {
            this.unsafe.putBoolean(this.clazz, this.fieldOffset, value);
        } else {
            this.unsafe.putBoolean(instance, this.fieldOffset, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Object instance) {
        if (this.isStatic) {
            return (T) this.unsafe.getObject(this.clazz, this.fieldOffset);
        }

        return (T) this.unsafe.getObject(instance, this.fieldOffset);
    }

    public byte getByte(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getByte(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getByte(instance, this.fieldOffset);
    }

    public short getShort(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getShort(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getShort(instance, this.fieldOffset);
    }

    public int getInt(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getInt(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getInt(instance, this.fieldOffset);
    }

    public long getLong(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getLong(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getLong(instance, this.fieldOffset);
    }

    public float getFloat(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getFloat(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getFloat(instance, this.fieldOffset);
    }

    public double getDouble(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getDouble(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getDouble(instance, this.fieldOffset);
    }

    public char getChar(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getChar(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getChar(instance, this.fieldOffset);
    }

    public boolean getBoolean(Object instance) {
        if (this.isStatic) {
            return this.unsafe.getBoolean(this.clazz, this.fieldOffset);
        }

        return this.unsafe.getBoolean(instance, this.fieldOffset);
    }
}

