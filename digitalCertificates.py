from OpenSSL import crypto
# from socket import gethostname
# from pprint import pprint
from time import gmtime
from os.path import exists, join

CERT_FILE = "x509.crt"
KEY_FILE = "x509.key"

def create_self_signed_cert(cert_dir):
    """
    If datacard.crt and datacard.key don't exist in cert_dir, create a new
    self-signed cert and keypair and write them into that directory.
    """

    if not exists(join(cert_dir, CERT_FILE)) or not exists(join(cert_dir, KEY_FILE)):

        # create a key pair
        k = crypto.PKey()
        k.generate_key(crypto.TYPE_RSA, 1024)     #You can use any cipher you want

        # create a self-signed cert
        cert = crypto.X509()
        cert.get_subject().C = "IN"   			#Country
        cert.get_subject().ST = "ST"				#STate
        cert.get_subject().L = "LLLL"			#Locality
        cert.get_subject().O = "OOOO"			#Organisation
        cert.get_subject().OU = "CBIT"			#Organisational Unit
        cert.get_subject().CN = "CNCNCNCN"		#Certificate Name
        cert.set_serial_number(1000)			#Serial Number
        cert.gmtime_adj_notBefore(0)			#Greenwich Meridian time not valid before
        cert.gmtime_adj_notAfter(10*365*24*60*60)	#Greenwich Meridian time not valid after
        cert.set_issuer(cert.get_subject())
        cert.set_pubkey(k)
        cert.sign(k, 'sha256')				#Signing with strong hash

#Write the certificate contents with the file headers as required

        open(join(cert_dir, CERT_FILE), "wt").write(
            crypto.dump_certificate(crypto.FILETYPE_PEM, cert))
        open(join(cert_dir, KEY_FILE), "wt").write(
            crypto.dump_privatekey(crypto.FILETYPE_PEM, k))
        
create_self_signed_cert(".")
