"""
Tuple: 'TS1384902645 ID12345 this is a sentence'

This program only generates the 'sentence' portion of the tuple
"""
import random as r
import argparse
import unicodedata

# The minimum number of words allowed per line
WORD_COUNT_MIN = 500

def main(args):
    fd = open(args.input, 'r')
    sentences = fd.readlines()
    total_sentences = len(sentences)
    fdt = open(args.output, 'w')

    for i in xrange(args.samples): 
        curr_sentence = ""
        while len(curr_sentence.split(" ")) < WORD_COUNT_MIN:
            idx = r.randint(1,total_sentences-1)
            curr_sentence += sentences[idx].strip() #does not include a '\n'
        fdt.write(curr_sentence + '\n')

    fdt.close()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Generate random sentence tuples from a dataset.')
    parser.add_argument('-s','--samples', help='Number of samples to write out', required=True, type=int)
    parser.add_argument('-o','--output', help='Output file name tuples', required=True)
    parser.add_argument('-i','--input', help='Input file name for sentence dataset', required=False, default='warandpeace.txt')
    args = parser.parse_args()
    main(args)
