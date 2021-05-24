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


if __name__ == '__main__':
    commit_name = get_commit_name(*sys.argv[1:])
    info = get_info(*sys.argv[1:])

    with(open("output.json", "w")) as f:
        data = {
            "commit_name": commit_name,
            "info": info["full"],
            "author": info["author"],
            "date": info["date"]
        }

        json.dump(data, f, indent=4)
        f.close()
