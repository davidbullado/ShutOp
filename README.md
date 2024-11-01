# ShutOp

`ShutOp` is a Java program that uses the ASM library to dynamically modify compiled `.class` files, allowing you to disable specific strings printed with `System.out.println`. This program takes a Java class file as input, identifies specified strings in methods, and conditionally replaces them.

## Features

- **Conditional string disabling**: Prevents specified strings (or all strings with `--all`) from being printed.
- **Bytecode manipulation**: Modifies `.class` files by removing print instructions.

## Prerequisites

- Java Development Kit (JDK)
- [ASM library](https://asm.ow2.io/) (used for bytecode manipulation)

## Compilation

To compile the program, ensure that the ASM library is included in the classpath:

```bash
javac -cp asm.jar:. org/davidbullado/PatchClass.java
```

## Usage

To run the program, use the following syntax:

```bash
java -cp asm.jar:. org.davidbullado.ShutOp <input_class> <output_class> <string_to_disable>
```

- `<input_class>`: Path to the input `.class` file.
- `<output_class>`: Path to the modified output `.class` file.
- `<string_to_disable>`: String to disable. Use `--all` to disable all strings.

### Example

To disable all occurrences of `"Hello, World!"` in a class `MyClass.class`:

```bash
java -cp asm.jar:. org.davidbullado.ShutOp MyClass.class MyClassPatched.class "Hello, World!"
```

To disable **all** strings in the `.class` file:

```bash
java -cp asm.jar:. org.davidbullado.ShutOp MyClass.class MyClassPatched.class --all
```

## How It Works

1. Loads the specified class file and initializes bytecode reading.
2. Iterates through methods to detect `System.out.println` instructions.
3. Disables printing of specified strings by altering the associated bytecode instructions.
4. Writes the modified `.class` file to the output location.

## Notes

- This program is intended for educational and demonstrative purposes on bytecode manipulation with ASM.
- Ensure you have the necessary permissions to read and write `.class` files.

## Dependencies

- **ASM**: A library for manipulating Java bytecode. Available for download [here](https://asm.ow2.io/).

## License

This project is licensed under the GNU General Public License v3.0. You may copy, distribute, and modify this program under the terms of the GPL-3.0 license. See the [LICENSE](LICENSE) file for more details.

For more information on the GNU GPL v3.0, visit the [GNU website](https://www.gnu.org/licenses/gpl-3.0.en.html).
