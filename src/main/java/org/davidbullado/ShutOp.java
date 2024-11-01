package org.davidbullado;

import org.objectweb.asm.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ShutOp {
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.err.println("Usage: ShutOp <input class> <output class> <string to disable>");
            System.exit(1);
        }

        FileInputStream fis = new FileInputStream(args[0]);
        ClassReader cr = new ClassReader(fis);
        ClassWriter cw = new ClassWriter(0);

        cr.accept(new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    private String stringValue = null;
                    @Override
                    public void visitLdcInsn(Object value) {
                        if (value instanceof String) {
                            stringValue = (String) value;
                        }
                        super.visitLdcInsn(value);
                    }
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        boolean isReplaced = false;
                        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/io/PrintStream") && name.equals("println")) {
                            if (stringValue != null) {
                                if (args[2].equals(stringValue) || args[2].equals("--all")) {
                                    System.out.println("String to be disabled: " + stringValue);
                                    mv.visitInsn(Opcodes.POP);
                                    mv.visitInsn(Opcodes.POP);
                                    mv.visitInsn(Opcodes.NOP);
                                    mv.visitInsn(Opcodes.NOP);
                                    isReplaced = true;
                                } else {
                                    System.out.println("String to be printed: " + stringValue);
                                }
                                stringValue = null;
                            }
                        }
                        if (!isReplaced) {
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    }
                };
            }
        }, 0);

        byte[] modifiedClass = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(args[1]);
        fos.write(modifiedClass);
        fos.close();
        fis.close();
    }
}