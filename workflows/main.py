import sys
import subprocess
import json


def get_commit_name(commit_hash):
    result = subprocess.run(f"git show -s --format=%s {commit_hash}",
                            stdout=subprocess.PIPE,
                            shell=True) \
        .stdout \
        .decode("utf-8") \
        .replace("\n", "")

    return result


def get_info(commit_hash):
    result = subprocess.run(f"git show {commit_hash}",
                            stdout=subprocess.PIPE,
                            shell=True) \
        .stdout \
        .decode("utf-8")

    author = result.splitlines(False)[1]
    date = result.splitlines(False)[2]

    return {
        "full": result,
        "author": author,
        "date": date
    }


def generate_description(info):
    return f"""## Commit information:
Name: {info['commit_name']}
Author: {info['author']}
[Filetree](https://github.com/NiChrosia/Acceleration/tree/{info['commit_sha']})
[Commit](https://github.com/NiChrosia/Acceleration/commit/{info['commit_sha']})"""


if __name__ == '__main__':
    commit_name = get_commit_name(*sys.argv[1:])
    info = get_info(*sys.argv[1:])

    f = open("output.json", "w+")

    data = {
        "commit_name": commit_name,
        "info": info["full"],
        "author": info["author"],
        "date": info["date"],
        "commit_sha": sys.argv[1]
    }

    description = generate_description(data)

    json.dump(description, f, indent=4)
    f.close()
