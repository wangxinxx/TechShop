import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPropertyString } from 'app/shared/model/item-property-string.model';
import { ItemPropertyStringService } from './item-property-string.service';

@Component({
    selector: 'jhi-item-property-string-delete-dialog',
    templateUrl: './item-property-string-delete-dialog.component.html'
})
export class ItemPropertyStringDeleteDialogComponent {
    itemPropertyString: IItemPropertyString;

    constructor(
        protected itemPropertyStringService: ItemPropertyStringService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPropertyStringService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemPropertyStringListModification',
                content: 'Deleted an itemPropertyString'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-property-string-delete-popup',
    template: ''
})
export class ItemPropertyStringDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyString }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemPropertyStringDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemPropertyString = itemPropertyString;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-property-string', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-property-string', { outlets: { popup: null } }]);
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
