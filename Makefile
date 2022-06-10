SRC_DIR := ./src
BIN_DIR := ./bin
FILES := $(shell find $(SRC_DIR) -name "*.java")

all: build
	cd ./bin && java Start ../Leistungsnachweis.csv ${CMD}

build: pre
	javac $(FILES) -d $(BIN_DIR)

pre:
	mkdir -p $(BIN_DIR)

clean:
	rm -r $(BIN_DIR)

.PHONY: clean pre build all
