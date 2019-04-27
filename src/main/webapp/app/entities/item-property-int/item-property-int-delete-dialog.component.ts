import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';
import { ItemPropertyIntService } from './item-property-int.service';

@Component({
    selector: 'jhi-item-property-int-delete-dialog',
    templateUrl: './item-property-int-delete-dialog.component.html'
})
export class ItemPropertyIntDeleteDialogComponent {
    itemPropertyInt: IItemPropertyInt;

    constructor(
        protected itemPropertyIntService: ItemPropertyIntService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPropertyIntService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemPropertyIntListModification',
                content: 'Deleted an itemPropertyInt'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-property-int-delete-popup',
    template: ''
})
export class ItemPropertyIntDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyInt }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemPropertyIntDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemPropertyInt = itemPropertyInt;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-property-int', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-property-int', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
