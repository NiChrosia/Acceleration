import sys
import json


def convert_string(sha_url):
    return f"https://github.com/NiChrosia/Acceleration/tree/{sha_url.split('/')[-1]}"


if __name__ == '__main__':
    string = convert_string(*sys.argv[1:])

    with(open("output.json", "w")) as f:
        json.dump(string, f, indent=4)
        f.close()
