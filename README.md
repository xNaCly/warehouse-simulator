# Warehouse-Simulator

This Java project was created in the second semester of [university](https://www.dhbw.de/startseite). It takes
[orders](./Leistungsnachweis.csv) in csv format and simulates a simplified warehouse workflow allowing you to manage
orders by moving pallets of products into empty slots in the storage, moving pallets of products out of the storage,
recycling pallets and rearranging them. Adding and removing pallets from the storage increase your bounty for the amount
specified in the order, recycling pallets deducts 300€ and moving pallets around in the storage costs 100€.

## Run locally:

### Dos (Windows):

```bat
mkdir ./bin
for /r src %i in (*.java) do javac %i -sourcepath src -d bin
cd ./bin
java Start ../Leistungsnachweis.csv
```

### With Make (Unix):

```bash
git clone https://github.com/xNaCly/warehouse-simulator ws
cd ws
make all
```

#### Makefile options:

Use `CMD` to pass command arguments:

```bash
make all CMD="--debug"
make all CMD="--silent"
```

Use `CSV` to specify a custom .csv file:
```bash
make all CSV="../../Test.csv"
```

Use `make test` to run tests.

## Cli reference:
- `-s` / `--silent` to suppress any logs (except errors)
- `-d` / `--debug` to display debug logs
