[![Code Smells][code_smells_badge]][code_smells_link]
[![Maintainability Rating][maintainability_rating_badge]][maintainability_rating_link]
[![Security Rating][security_rating_badge]][security_rating_link]
[![Bugs][bugs_badge]][bugs_link]
[![Vulnerabilities][vulnerabilities_badge]][vulnerabilities_link]
[![Duplicated Lines (%)][duplicated_lines_density_badge]][duplicated_lines_density_link]
[![Reliability Rating][reliability_rating_badge]][reliability_rating_link]
[![Quality Gate Status][quality_gate_status_badge]][quality_gate_status_link]
[![Technical Debt][technical_debt_badge]][technical_debt_link]
[![Lines of Code][lines_of_code_badge]][lines_of_code_link]

Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - TI/ТИ (теория информации), ранее назывался KiOKI/КиОКИ.

## Условия

### Лабораторная работа 1

Написать программу, которая выполняет шифрование и дешифрование текстового файла любого размера, содержащего текст на
заданном языке, используя следующие алгоритмы шифрования:

* «Столбцовый метод» с одним ключевым словом, текст на русском языке;
* Алгоритм Виженера, самогенерирующийся ключ, текст на русском языке.

Для всех алгоритмов ключ задается с клавиатуры пользователем.

Программа должна игнорировать все символы, не являющиеся буквами заданного алфавита, и шифровать только текст на
заданном языке.
Все алгоритмы должны быть реализованы в одной программе.
Программа не должна быть написана в консольном режиме.
Результат работы программы – зашифрованный/расшифрованный файл/ы.

### Лабораторная работа 2

Реализовать систему потокового шифрования и дешифрования для файла с любым содержимым с помощью генератора ключевой
последовательности на основе линейного сдвигового регистра с обратной связью LFSR1.
Начальное состояние регистра ввести с клавиатуры. Поле для ввода состояния регистра должно игнорировать любые символы
кроме 0 и 1.
Вывести на экран сгенерированный ключ (последовательность из 0 и 1), исходный файл и зашифрованный файл в двоичном виде.
Программа не должна быть написана в консольном режиме. Результат работы программы – зашифрованный/расшифрованный файл.

### Лабораторная работа 3

Реализовать шифратор и дешифратор по алгоритму Рабина файла с любым содержимым, используя расширенный алгоритм Евклида и
алгоритм быстрого возведения в степень при дешифрации.
Значения параметров p, q и (b) задаются пользователем.
Программа должна осуществлять проверку ограничений на вводимые пользователем значения параметров.
Организовать вывод содержимого зашифрованного файла на экран в виде чисел в 10-й системе счисления.
Результат работы программы – зашифрованный/расшифрованный файл/ы.
При использовании длинной арифметики для определения простоты числа использовать один из вероятностных тестов: тест
Ферма или тест Миллера-Рабина.

### Лабораторная работа 4

Реализовать программное средство, выполняющее вычисление и проверку электронной цифровой подписи (ЭЦП) текстового файла
на базе алгоритма DSA.
Для вычисления хеш-образа сообщения использовать функцию 3.2 из методических материалов (стр.22, Н0=100), вычисления
функции необходимо выполнять по модулю числа q.
Числа q, p, h, x и k ввести с клавиатуры.
Произвести все необходимые проверки для параметров, вводимых с клавиатуры.
В отдельное поле вывести полученный хеш сообщения в 10 с/cч.
ЭЦП вывести как два целых числа (если одно из полученных значений r или s будет равно 0, то необходимо повторить
вычисления).
Сформировать новое сообщение, состоящее из исходного сообщения и добавленной к нему цифровой подписи.
При проверке ЭЦП предусмотреть возможность выбора файла для проверки. На экран вывести результат проверки:

* сообщение о том, верна подпись или нет;
* вычисленные при проверке значения.

Для возведения в степень использовать быстрый алгоритм возведения в степень по модулю.
При нахождении обратного элемента s−1mod q или k−1 mod q использовать малую теорему Ферма в виде: s−1mod q = sq-2 mod q.

<!----------------------------------------------------------------------------->

[code_smells_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=code_smells

[code_smells_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[maintainability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=sqale_rating

[maintainability_rating_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[security_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=security_rating

[security_rating_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[bugs_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=bugs

[bugs_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[vulnerabilities_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=vulnerabilities

[vulnerabilities_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[duplicated_lines_density_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=duplicated_lines_density

[duplicated_lines_density_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[reliability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=reliability_rating

[reliability_rating_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[quality_gate_status_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=alert_status

[quality_gate_status_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[technical_debt_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=sqale_index

[technical_debt_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory

[lines_of_code_badge]: https://sonarcloud.io/api/project_badges/measure?project=hummel009_Information-Theory&metric=ncloc

[lines_of_code_link]: https://sonarcloud.io/summary/overall?id=hummel009_Information-Theory
