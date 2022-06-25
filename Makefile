SRC_DIR := ./src
BIN_DIR := ./bin
FILES := $(shell find $(SRC_DIR) -name "*.java")
CSV := ../Leistungsnachweis.csv

all: build
	cd ${BIN_DIR} && java Start $(CSV) ${CMD}

test: build
	cd ${BIN_DIR} && java Test ../Leistungsnachweis.csv

build: pre
	javac $(FILES) -d $(BIN_DIR)

pre:
	mkdir -p $(BIN_DIR)

clean:
	rm -r $(BIN_DIR)
	rm -r ./out

.PHONY: clean pre build all
