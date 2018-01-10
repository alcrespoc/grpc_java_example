# Copyright 2015 gRPC authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""The Python implementation of the GRPC helloworld.Greeter client."""

from __future__ import print_function

import grpc

import saludo_pb2
import saludo_pb2_grpc


def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = saludo_pb2_grpc.SaludarStub(channel)
  response = stub.Saludo(saludo_pb2.SaludoRequest(nombre='Nombre', apellido1='Apellido1', apellido2='Apellido2', dni='Python'))
  print("Saludo recibido: " + response.message)


if __name__ == '__main__':
  run()
