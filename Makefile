SRC_DIR := ./src
BIN_DIR := ./bin
FILES := $(shell find $(SRC_DIR) -name "*.java")

all: clean build 
	cd ./bin && java Start ../Leistungsnachweis.csv

build: pre
	javac $(FILES) -d $(BIN_DIR)

pre:
	mkdir -p $(BIN_DIR)

clean:
	rm -r $(BIN_DIR)

.PHONY: clean pre build all
