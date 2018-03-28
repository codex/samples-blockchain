#!/usr/bin/env bash
## Remove the  LMDB files
echo "Removing LMDB Files ..."
rm /var/lib/sawtooth/*lmdb*
## Remove the chain Id.
echo "Removing chain Id ..."
rm /var/lib/sawtooth/block-chain-id
cd ~
## set up  new keys for user
echo "Overwriting user with new keys ...."
sawtooth keygen --force
## set up genesis file 
echo "Creating the genesis config file ..."
sawset genesis
## set up genesis block
echo "Setting up genesis file .."
sudo -u sawtooth sawadm genesis config-genesis.batch
echo "The chain is reset you can now start the validator..."


