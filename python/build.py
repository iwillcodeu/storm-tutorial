from pybuilder.core import use_plugin, init

use_plugin("python.core")
use_plugin("python.unittest")
use_plugin("python.install_dependencies")
use_plugin("python.distutils")


name = "python"
default_task = "publish"


@init
def initialize(project):
    project.build_depends_on("kafka-python")

def set_properties(project):
    pass
